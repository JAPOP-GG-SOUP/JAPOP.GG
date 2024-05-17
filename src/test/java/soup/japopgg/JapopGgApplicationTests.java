package soup.japopgg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soup.japopgg.crawlPatchNote.dto.PatchNoteDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedAbilityDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedChampionDTO;
import soup.japopgg.crawlPatchNote.service.CrawlingService;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class JapopGgApplicationTests {
	@Autowired
	CrawlingService crawlingService;

	@Test
	void parsePatchNoteListTest() throws IOException {
		PatchNoteDTO testDTO = crawlingService.crawlLatestPatchNoteTitle();
		System.out.println(testDTO.getPatchNotePath());
		System.out.println(testDTO.getPatchNoteTitle());
		System.out.println(testDTO.getPatchDate());
	}

	@Test
	void parsePatchNoteTest() throws IOException{
		String testPath = "ko-kr/news/game-updates/patch-14-9-notes/";
		String testTitle = "14.10 패치노트";
		List<PatchedChampionDTO> testList = crawlingService.crawlPatchNote(testPath,testTitle);
		for(PatchedChampionDTO champ: testList){
			System.out.println(champ.getPatchNoteTitle());
			System.out.println(champ.getChampion());
			for(PatchedAbilityDTO ability: champ.getPatchInfo()){
				System.out.println(ability.getPatchedAbility());
				for(String detail: ability.getPatchDetails())
					System.out.println("\t"+detail);
			}
		}
	}
}
