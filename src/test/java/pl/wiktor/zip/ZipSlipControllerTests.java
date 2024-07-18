package pl.wiktor.zip;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ZipSlipController.class)
public class ZipSlipControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldUploadAndUnzipFile() throws Exception {
        try (FileInputStream fis = new FileInputStream("src/test/resources/zip_slip_normal.zip")) {
            MockMultipartFile mockFile = new MockMultipartFile("file", "normal.zip", "application/zip", fis);

            mockMvc.perform(multipart("/api/upload").file(mockFile))
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void shouldRejectZipSlipAttack() throws Exception {
        try (FileInputStream fis = new FileInputStream("src/test/resources/zip_slip_attack.zip")) {
            MockMultipartFile mockFile = new MockMultipartFile("file", "zip_slip_attack.zip", "application/zip", fis);

            mockMvc.perform(multipart("/api/upload").file(mockFile))
                    .andExpect(status().isBadRequest());
        }
    }
}
