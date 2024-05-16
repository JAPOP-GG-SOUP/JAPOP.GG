package soup.japopgg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "crawl")
@Data
public class CrawlConfig {
    private BasePathConfig path;
    private ListValueConfig listPageValues;
    private PatchNoteValueConfing patchNoteValues;

    @Data
    public static class BasePathConfig{
        private String baseUrl;
        private String listPath;
    }

    @Data
    public static class ListValueConfig{
        private String listElementClass;
        private String listPathClass;
        private String listPathElement;
        private String patchNoteTitleClass;
        private String patchNoteDateClass;
        private String patchNoteDateElement;
    }

    @Data
    public static class PatchNoteValueConfing{
        private String selectorByKeyword;
        private String processClass;
        private String processEndTag;
        private String champNameClass;
        private String champSkillClass;
        private String champPatchDetailTag;
    }
}
