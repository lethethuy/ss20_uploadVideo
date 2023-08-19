package ra.uploadvideo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ra.uploadvideo.model.Video;
import ra.uploadvideo.model.VideoDTO;
import ra.uploadvideo.service.VideoService;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/")
@PropertySource("classpath:upload.properties")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("list", videoService.findAll());
        return "list";

    }

    @GetMapping("/upload")
    public ModelAndView upload() {

        return new ModelAndView("upload", "video", new VideoDTO());
    }
    //"upload": Đây là tên của view (giao diện) mà phương thức sẽ trả về.
    // Trong trường hợp này, có thể đang tham chiếu đến một tập tin HTML hoặc JSP để hiển thị giao diện cho trang tải lên video.
    //
    //"video": Đây là tên của một attribute (thuộc tính) được đặt trong ModelAndView.
    // Attribute này sẽ được sử dụng trong view để truy cập đối tượng VideoDto.
    //
    //new VideoDto(): Đây là đối tượng mới của lớp VideoDto được tạo ra.
    // VideoDto có thể là một lớp DTO (Data Transfer Object) để lưu trữ thông tin về video cần tải lên.
    // Đối tượng này sẽ được truyền vào view để dữ liệu có thể được hiển thị hoặc nhập liệu từ người dùng.


    @Value("${upload-path}")
    private String pathUpload;

    //Khi post len database thi can chuyen sang chuoi string de luu
    @PostMapping("/upload")
    public String doUpload(@ModelAttribute("video") VideoDTO videoDTO) {
        // upload file
        File file = new File(pathUpload);
        if (!file.exists()) {
            // chưa tồn tại folder , khởi tạo 1 folder mới
            file.mkdirs();
        }
        String fileName = videoDTO.getVideoUrl().getOriginalFilename();
        try {
            FileCopyUtils.copy(videoDTO.getVideoUrl().getBytes(), new File(pathUpload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // chuyen doi sang video
        Video newVideo = new Video();
        newVideo.setName(fileName);
        newVideo.setTitle(videoDTO.getTitle());
        newVideo.setDescription(videoDTO.getDescription());
        videoService.save(newVideo);
        return "redirect:/";
    }

}
