package soup.japopgg.crawlPatchNote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import soup.japopgg.crawlPatchNote.config.CrawlConfig;
import soup.japopgg.crawlPatchNote.dto.PatchedChampionDTO;
import soup.japopgg.crawlPatchNote.dto.PatchNoteDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedAbilityDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrawlingService {

    private final CrawlConfig crawlConfig;

    public PatchNoteDTO crawlLatestPatchNoteTitle() throws IOException {
        String patchNoteListUrl = crawlConfig.getPath().getBaseUrl() + crawlConfig.getPath().getListPath();

        Document document = Jsoup.connect(patchNoteListUrl).get();
        Elements patchListElements = document.select(crawlConfig.getListPageValues().getListElementClass());
        if (patchListElements.isEmpty()) {
            return null;
        }

        Element firstPatchNoteElement = patchListElements.get(0);
        Element pathElement = firstPatchNoteElement.selectFirst(crawlConfig.getListPageValues().getListPathClass());

        if (pathElement != null) {
            String patchNotePath = pathElement.attr(crawlConfig.getListPageValues().getListPathElement());
            String patchNoteTitle = firstPatchNoteElement.select(crawlConfig.getListPageValues().getPatchNoteTitleClass()).text();
            String dateString = firstPatchNoteElement.select(crawlConfig.getListPageValues().getPatchNoteDateClass()).attr(crawlConfig.getListPageValues().getPatchNoteDateElement());

            LocalDate patchDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);
            return new PatchNoteDTO(patchNotePath,patchNoteTitle,patchDate);
        }

        return null;
    }

    public List<PatchedChampionDTO> crawlPatchNote(String patchNotePath, String patchNoteTitle, LocalDate patchDate) throws IOException {
        List<PatchedChampionDTO> patches = new ArrayList<>();
        String patchNoteUrl = crawlConfig.getPath().getBaseUrl() + patchNotePath;

        Document doc = Jsoup.connect(patchNoteUrl).get();
        Elements headers = doc.select(crawlConfig.getPatchNoteValues().getSelectorByKeyword());

        String processEndTag = crawlConfig.getPatchNoteValues().getProcessEndTag();
        String processElementClass = crawlConfig.getPatchNoteValues().getProcessClass();
        String champNameClass = crawlConfig.getPatchNoteValues().getChampNameClass();
        String champSkillClass = crawlConfig.getPatchNoteValues().getChampSkillClass();
        String champPatchDetailTag = crawlConfig.getPatchNoteValues().getChampPatchDetailTag();

        for (Element header : headers) {
            Element champPatchTag = header.nextElementSibling();
            while (champPatchTag != null && !champPatchTag.tagName().equalsIgnoreCase(processEndTag)) {
                Elements patchChangeBlocks = champPatchTag.select(processElementClass);
                if (!patchChangeBlocks.isEmpty()) {
                    String championName = champPatchTag.select(champNameClass).text();
                    List<PatchedAbilityDTO> championPatchDetails = new ArrayList<>();
                    Elements changeTitles = champPatchTag.select(champSkillClass);

                    for (Element changeTitle : changeTitles) {
                        String ability = changeTitle.text();
                        Elements changeDetails = changeTitle.nextElementSibling().select(champPatchDetailTag);
                        List<String> abilityPatchDetails = new ArrayList<>();
                        for (Element detailLine : changeDetails)
                            abilityPatchDetails.add(detailLine.text());
                        championPatchDetails.add(new PatchedAbilityDTO(ability, abilityPatchDetails));
                    }

                    PatchedChampionDTO existingChampionPatch = null;
                    for (PatchedChampionDTO patch : patches) {
                        if (patch.getChampion().equals(championName)) {
                            existingChampionPatch = patch;
                            break;
                        }
                    }

                    if (existingChampionPatch != null)
                        existingChampionPatch.getPatchInfo().addAll(championPatchDetails);
                    else
                        patches.add(new PatchedChampionDTO(championName, patchNoteTitle, patchDate, championPatchDetails));
                }
                champPatchTag = champPatchTag.nextElementSibling();
            }
        }

        return patches;
    }

}
