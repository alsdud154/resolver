package kr.co.velnova.resolver.controller;

import kr.co.velnova.resolver.model.GridRequest;
import kr.co.velnova.resolver.model.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResolverController {

    @GetMapping("/member")
    public GridRequest<Member> resolver(GridRequest<Member> gridRequest){
        System.out.println("gridRequest = " + gridRequest);
        return gridRequest;
    }
}
