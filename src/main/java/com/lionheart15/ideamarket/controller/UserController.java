package com.lionheart15.ideamarket.controller;

import com.lionheart15.ideamarket.domain.dto.BoardListResponse;
import com.lionheart15.ideamarket.domain.dto.UserSignUpDto;
import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.Comment;
import com.lionheart15.ideamarket.domain.entity.Good;
import com.lionheart15.ideamarket.domain.entity.User;
import com.lionheart15.ideamarket.service.BoardService;
import com.lionheart15.ideamarket.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final BoardService boardService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            log.info("auth name : {}", auth.getName());
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
    @PostMapping("/create")
    public  String create(@ModelAttribute UserSignUpDto dto, Model model){

        if(userService.checkUsernameDuplication(dto.getLoginId())){
            model.addAttribute("iddup", true);
            return "signup";
        }

        userService.save(dto);
        return"login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/{userId}/myPage")

    public String myPage(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);

        List<Board> boardList = user.getBoards();
        model.addAttribute("boardList",boardList);
        //user 엔티티에 매핑되어있는 board 에 대한 값들을 저장
        List<Comment> commentList = user.getComments();
        model.addAttribute("commentList",commentList);
        //user 엔티티에 매핑되어있는 comment 에 대한 값들 저장


        List<Good> goodList = user.getGoods();

        List<BoardListResponse> boardGoodsList = new ArrayList<>();
        for(int i = 0; i<goodList.size();i++){
            boardGoodsList.add(BoardListResponse.of(goodList.get(i).getBoard()));
        }
        //user 가 좋아요한 리스트를 뽑아서 response 형태로 변환해 무스타치로 넘김
        model.addAttribute("boardGoodsList",boardGoodsList);
        return "mypage01";
    }
}
