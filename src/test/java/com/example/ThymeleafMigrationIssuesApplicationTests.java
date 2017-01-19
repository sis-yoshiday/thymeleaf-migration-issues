package com.example;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * execute test and output results to ${project_root}/results/*.txt
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ThymeleafMigrationIssuesApplication.MyController.class)
public class ThymeleafMigrationIssuesApplicationTests {

  @Autowired
  private MockMvc mvc;

  /**
   * th:remove="all-but-first" incompatibility
   */
  @Test
  public void th_remove() throws Exception {
    test("/th_remove");
  }

  /**
   * th:inline="javascript" incompatibility
   */
  @Test
  public void th_inline_js() throws Exception {
    test("/th_inline_js");
  }

  private void test(String path) throws Exception {

    mvc.perform(get(path).accept(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andDo(result -> {

          Version version = Version.get();

          String fileName = version.resultFileName(path);

          Files.deleteIfExists(Paths.get(fileName));

          try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(result.getResponse().getContentAsString().getBytes());
          }
        });
  }

  private enum Version {
    TH2("results/%s.th2.txt"),
    TH3("results/%s.th3.txt");

    private final String resultFileNameFormat;

    Version(String resultFileNameFormat) {
      this.resultFileNameFormat = resultFileNameFormat;
    }

    private static Version get() {

      try {
        Class.forName("org.thymeleaf.templatemode.TemplateMode");
        return TH3;
      } catch (ClassNotFoundException e) {
        return TH2;
      }
    }

    private String resultFileName(String path) {
      return String.format(resultFileNameFormat, path.replace("/", ""));
    }
  }
}
