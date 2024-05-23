package soup.japopgg.searchSummoner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import soup.japopgg.searchSummoner.RiotHttpClientService;
import soup.japopgg.searchSummoner.dto.AccountDto;
import soup.japopgg.searchSummoner.dto.ResponseDto;
import soup.japopgg.searchSummoner.dto.SummonerDto;
import soup.japopgg.searchSummoner.dto.SummonerInformationDto;
import soup.japopgg.searchSummoner.repository.SummonerRepository;
import soup.japopgg.searchSummoner.entity.SummonerInformation;

import java.util.Optional;

@Service
public class SummonerInformationService {
    private final SummonerRepository summonerRepository;
    private final RiotHttpClientService riotHttpClientService;
    private final ObjectMapper mapper;

    public SummonerInformationService(SummonerRepository summonerRepository, RiotHttpClientService riotHttpClientService, ObjectMapper mapper) {
        this.summonerRepository = summonerRepository;
        this.riotHttpClientService = riotHttpClientService;
        this.mapper = mapper;
    }

    // name, tag를 사용해 SummonerInformation 데이터베이스에 저장
    public String saveSummonerInformation(String name, String tag) throws JsonProcessingException {

        ResponseDto responseDto = null;
        AccountDto accountDto = null;
        SummonerDto summonerDto = null;
        SummonerInformationDto summonerInformationDto = null;

        // 소환사명, 태그로 puuid GET
        responseDto = riotHttpClientService.getAccountsByNameAndTag(name, tag); //status = responseDto.getStatus();
        if (responseDto.isOK()) { // true->응답 정상, false->응답 오류

            accountDto = mapper.readValue(responseDto.getResponseBody(), AccountDto.class);
        }

        // puuid로 소환사 정보 GET
        responseDto = riotHttpClientService.getSummonersByPuuid(accountDto.getPuuid()); //status = responseDto.getStatus();
        if (responseDto.isOK()) {
            summonerDto = mapper.readValue(responseDto.getResponseBody(), SummonerDto.class);
        }

        if (accountDto != null && summonerDto != null) {
            summonerInformationDto = new SummonerInformationDto(
                    summonerDto.getPuuid(),
                    summonerDto.getId(),
                    summonerDto.getAccountId(),
                    summonerDto.getProfileIconId(),
                    summonerDto.getRevisionDate(),
                    summonerDto.getSummonerLevel(),
                    accountDto.getGameName(),
                    accountDto.getTagLine()
            );

            // DTO를 엔티티로 변환
            SummonerInformation summonerInformation = convertToEntity(summonerInformationDto);

            // 엔티티를 데이터베이스에 저장
            summonerRepository.save(summonerInformation);
        }

        return summonerInformationDto.getPuuid();
    }

    // puuid를 사용해 데이터베이스에서 SummonerInformation 가져옴
    public SummonerInformationDto getSummonerInformation(String puuid) {
        Optional<SummonerInformation> summonerInformationOptional = summonerRepository.findByPuuid(puuid);

        if (summonerInformationOptional.isPresent()) {
            SummonerInformation summonerInformation = summonerInformationOptional.get();
            return convertToDto(summonerInformation);
        } else {
            // 데이터가 없을 경우 null을 반환하거나, 예외를 던질 수 있음
            return null;
        }
    }

    // Dto -> Entity
    private SummonerInformation convertToEntity(SummonerInformationDto dto) {
        SummonerInformation entity = new SummonerInformation();
        entity.setPuuid(dto.getPuuid());
        entity.setId(dto.getId());
        entity.setAccountId(dto.getAccountId());
        entity.setProfileIconId(dto.getProfileIconId());
        entity.setRevisionDate(dto.getRevisionDate());
        entity.setSummonerLevel(dto.getSummonerLevel());
        entity.setGameName(dto.getGameName());
        entity.setTagLine(dto.getTagLine());
        return entity;
    }

    // Entity -> Dto
    private SummonerInformationDto convertToDto(SummonerInformation summonerInformation) {
        SummonerInformationDto dto = new SummonerInformationDto();
        dto.setPuuid(summonerInformation.getPuuid());
        dto.setId(summonerInformation.getId());
        dto.setAccountId(summonerInformation.getAccountId());
        dto.setProfileIconId(summonerInformation.getProfileIconId());
        dto.setRevisionDate(summonerInformation.getRevisionDate());
        dto.setSummonerLevel(summonerInformation.getSummonerLevel());
        dto.setGameName(summonerInformation.getGameName());
        dto.setTagLine(summonerInformation.getTagLine());
        return dto;
    }
}
