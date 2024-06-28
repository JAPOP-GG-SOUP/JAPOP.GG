package soup.japopgg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soup.japopgg.crawlPatchNote.dto.PatchNoteDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedAbilityDTO;
import soup.japopgg.crawlPatchNote.dto.PatchedChampionDTO;
import soup.japopgg.crawlPatchNote.service.CrawlingService;
import soup.japopgg.crawlPatchNote.service.PatchDataService;
import soup.japopgg.crawlPatchNote.service.SearchPatchService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class JapopGgApplicationTests {
	@Autowired
	CrawlingService crawlingService;
	@Autowired
	PatchDataService patchDataService;
	@Autowired
	SearchPatchService searchPatchService;

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
		LocalDate testDate = LocalDate.now();

		List<PatchedChampionDTO> testList = crawlingService.crawlPatchNote(testPath,testTitle,testDate);
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

	@Test
	void savePatchNoteTest() throws IOException{
		String testPath = "ko-kr/news/game-updates/patch-14-9-notes/";
		String testTitle = "14.10 패치노트";
		LocalDate testDate = LocalDate.now();

		patchDataService.savePatchNoteData(new PatchNoteDTO(testPath,testTitle,testDate));
		List<PatchNoteDTO> testList = searchPatchService.getPatchNoteList();
		for(PatchNoteDTO result:testList)
			System.out.println(result.getPatchNoteTitle());
	}

	@Test
	void deletePatchNoteTest() throws IOException{
		String testTitle = "14.10 패치노트";
		patchDataService.deletePatchNoteData(testTitle);
	}

	@Test
	void saveChampionPatchTest() throws IOException{
		String testPath = "ko-kr/news/game-updates/patch-14-9-notes/";
		String testTitle = "14.10 패치노트";
		LocalDate testDate = LocalDate.now();

		List<PatchedChampionDTO> testList = crawlingService.crawlPatchNote(testPath,testTitle,testDate);
		for(PatchedChampionDTO champ: testList)
			patchDataService.savePatchedChampionData(champ);
	}

	@Test
	void deleteChampionPatchTest() throws IOException{
		String testTitle = "14.10 패치노트";
		patchDataService.deletePatchedChampionData(testTitle,"아크샨");
	}
}
