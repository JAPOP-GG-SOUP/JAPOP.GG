package soup.japopgg.crawlPatchNote.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soup.japopgg.crawlPatchNote.dto.PatchNoteDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedAbilityDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedChampionDTO;
import soup.japopgg.crawlPatchNote.entity.PatchNote;
import soup.japopgg.crawlPatchNote.entity.PatchedChampion;
import soup.japopgg.crawlPatchNote.repository.PatchNoteRepository;
import soup.japopgg.crawlPatchNote.repository.PatchedChampionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchPatchService {
    private final ObjectMapper objectMapper;
    private final PatchNoteRepository patchNoteRepo;
    private final PatchedChampionRepository patchedChampionRepo;

    @Transactional(readOnly = true)
    public List<PatchNoteDTO> getPatchNoteList(){
        List<PatchNote> patchNotes = patchNoteRepo.findAllOrderByPatchDateDesc();
        List<PatchNoteDTO> result = patchNotes.stream()
                .map(patchNote -> new PatchNoteDTO(
                        patchNote.getPatchNotePath(),
                        patchNote.getPatchNoteTitle(),
                        patchNote.getPatchDate()
                ))
                .collect(Collectors.toList());

        return result;
    }

    @Transactional(readOnly = true)
    public List<PatchedChampionDTO> getPatchedChampionList(String patchNoteTitle){
        List<PatchedChampion> patchedChampions = patchedChampionRepo.findAllByPatchNoteTitleOrderByChampionDesc(patchNoteTitle);
        return convertPatchedChampionEntityToDto(patchedChampions);
    }

    @Transactional(readOnly = true)
    public List<PatchedChampionDTO> getChampionPatchHistory(String championName){
        List<PatchedChampion> patchedChampions = patchedChampionRepo.findAllByChampionOrderByPatchDateDesc(championName);
        return convertPatchedChampionEntityToDto(patchedChampions);
    }

    private List<PatchedChampionDTO> convertPatchedChampionEntityToDto(List<PatchedChampion> patchedChampions){
        List<PatchedChampionDTO> result = patchedChampions.stream()
                .map(champion -> new PatchedChampionDTO(
                        champion.getChampion(),
                        champion.getPatchNoteTitle(),
                        champion.getPatchDate(),
                        champion.getPatchInfo().stream()
                                .map(patchedAbility -> {
                                    try {
                                        return new PatchedAbilityDTO(
                                                patchedAbility.getPatchedAbility(),
                                                objectMapper.readValue(patchedAbility.getPatchDetails(), List.class)
                                        );
                                    } catch (JsonProcessingException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return result;
    }
}
