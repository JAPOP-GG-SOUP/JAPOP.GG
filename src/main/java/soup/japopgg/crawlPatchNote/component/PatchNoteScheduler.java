package soup.japopgg.crawlPatchNote.component;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import soup.japopgg.crawlPatchNote.dto.PatchNoteDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedChampionDTO;
import soup.japopgg.crawlPatchNote.service.CrawlingService;
import soup.japopgg.crawlPatchNote.service.PatchDataService;
import soup.japopgg.crawlPatchNote.service.SearchPatchService;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PatchNoteScheduler {
    private final CrawlingService crawlingService;
    private final PatchDataService patchDataService;
    private final SearchPatchService searchPatchService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updatePatchNote() throws IOException {
        PatchNoteDTO crawledDTO = crawlingService.crawlLatestPatchNoteTitle();
        PatchNoteDTO latestPatchNoteDTOInDatabase = searchPatchService.getCurrentPatchNote();
        if(!crawledDTO.getPatchNoteTitle().equals(latestPatchNoteDTOInDatabase.getPatchNoteTitle())){
            List<PatchedChampionDTO> patchList = crawlingService.crawlPatchNote(
                    crawledDTO.getPatchNotePath(),crawledDTO.getPatchNoteTitle(),crawledDTO.getPatchDate());
            for(PatchedChampionDTO patchData:patchList)
                patchDataService.savePatchedChampionData(patchData);
        }
    }
}
