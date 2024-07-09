package soup.japopgg.crawlPatchNote.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soup.japopgg.crawlPatchNote.dto.PatchNoteDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedChampionDTO;
import soup.japopgg.crawlPatchNote.service.SearchPatchService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatchNoteController {
    private final SearchPatchService searchPatchService;

    @GetMapping("/getPatchNoteList")
    public List<PatchNoteDTO> patchNoteList(){
        return searchPatchService.getPatchNoteList();
    }

    @GetMapping("/getPatchNote")
    public List<PatchedChampionDTO> patchNoteInfo(@RequestParam String patchNoteName){
        return searchPatchService.getPatchedChampionList(patchNoteName);
    }

    @GetMapping("/getChampionPatchHistory")
    public List<PatchedChampionDTO> championPatchHistory(@RequestParam String championName){
        return searchPatchService.getChampionPatchHistory(championName);
    }
}
