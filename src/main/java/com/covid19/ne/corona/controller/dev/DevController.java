package com.covid19.ne.corona.controller.dev;

/* sanjayda created on 4/5/2020 inside the package - com.covid19.ne.corona.controller.dev */

import com.covid19.ne.corona.aspects.HitCount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
public class DevController {
    @GetMapping("/totalhit/details")
    public Object getHitDetails() {
        return HitCount.getHitDetails();
    }
}


