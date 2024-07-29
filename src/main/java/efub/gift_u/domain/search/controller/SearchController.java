package efub.gift_u.domain.search.controller;

import efub.gift_u.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    /* 펀딩 검색 */
    @GetMapping
    public ResponseEntity<?> SearchAllByWord(@RequestParam(name ="word") String searchWord){
         return searchService.Search(searchWord);
    }
}
