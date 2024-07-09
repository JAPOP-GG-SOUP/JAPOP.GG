package soup.japopgg.crawlPatchNote.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soup.japopgg.crawlPatchNote.dto.PatchNoteDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedAbilityDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedChampionDTO;
import soup.japopgg.crawlPatchNote.entity.PatchNote;
import soup.japopgg.crawlPatchNote.entity.PatchedAbility;
import soup.japopgg.crawlPatchNote.entity.PatchedChampion;
import soup.japopgg.crawlPatchNote.repository.PatchNoteRepository;
import soup.japopgg.crawlPatchNote.repository.PatchedChampionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatchDataService {

    private final ObjectMapper objectMapper;
    private final PatchNoteRepository patchNoteRepo;
    private final PatchedChampionRepository patchedChampionRepo;

    @CacheEvict(value = "patchNoteList", allEntries = true)
    @Transactional
    public void savePatchNoteData(PatchNoteDTO patchNoteDTO){
        PatchNote patchNote=new PatchNote(patchNoteDTO.getPatchNotePath(),
                patchNoteDTO.getPatchNoteTitle(),
                patchNoteDTO.getPatchDate());

        patchNoteRepo.save(patchNote);
    }

    @CacheEvict(value = "championPatchHistory", key = "#patchedChampionDTO.champion")
    @Transactional
    public void savePatchedChampionData(PatchedChampionDTO patchedChampionDTO) throws JsonProcessingException {
        PatchedChampion patchedChampion=new PatchedChampion(patchedChampionDTO.getChampion(),
                patchedChampionDTO.getPatchNoteTitle(),
                patchedChampionDTO.getPatchDate(),
                new ArrayList<PatchedAbility>());

        for(PatchedAbilityDTO patchedAbilityDTO:patchedChampionDTO.getPatchInfo()){
            PatchedAbility patchedAbility=new PatchedAbility(
                    patchedAbilityDTO.getPatchedAbility(),
                    objectMapper.writeValueAsString(patchedAbilityDTO.getPatchDetails()),
                    patchedChampion);
            System.out.println(patchedAbility.getPatchDetails());
            patchedChampion.getPatchInfo().add(patchedAbility);
        }

        patchedChampionRepo.save(patchedChampion);
    }

    @Caching(evict = {
            @CacheEvict(value = "patchNoteList", allEntries = true),
            @CacheEvict(value = "patchedChampionList", key = "#patchNoteTitle")
    })
    @Transactional
    public void deletePatchNoteData(String patchNoteTitle){
        patchNoteRepo.deleteByPatchNoteTitle(patchNoteTitle);
    }

    @Caching(evict = {
            @CacheEvict(value = "championPatchHistory", key = "#champion"),
            @CacheEvict(value = "patchedChampionList", key = "#patchNoteTitle")
    })
    @Transactional
    public void deletePatchedChampionData(String patchNoteTitle,String champion){
        patchedChampionRepo.deleteByPatchNoteTitleAndChampion(patchNoteTitle,champion);
    }
}
