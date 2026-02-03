package com.izakaya.sion.controller;

import com.izakaya.sion.entity.StoreEntity;
import com.izakaya.sion.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ServiceController {

    private final StoreRepository storeRepository;

    @GetMapping("/service")
    public String service() {
        return "service/service"; // templates/service.html
    }

     //전체: /service/map
     //도시: /service/map?city=TOKYO	/service/map?city=OSAKA	/service/map?city=FUKUOKA
     
    @GetMapping("/service/map")
    public String map(
            @RequestParam(name = "city", required = false) String city,
            Model model
    ) {
        final List<StoreEntity> stores;

        if (city == null || city.isBlank() || city.equalsIgnoreCase("ALL")) {
            stores = storeRepository.findAllByOrderByIdAsc();
        } else {
            stores = storeRepository.findByCityIgnoreCaseOrderByIdAsc(city.trim());
        }

        // 지도 중심(Ginza)
        double centerLat = 35.6723723;
        double centerLng = 139.7654357;

        if (!stores.isEmpty() && stores.get(0).getLat() != null && stores.get(0).getLng() != null) {
            centerLat = stores.get(0).getLat();
            centerLng = stores.get(0).getLng();
        }

        model.addAttribute("stores", stores);
        model.addAttribute("selectedCity",
                (city == null || city.isBlank()) ? "ALL" : city.trim().toUpperCase());
        model.addAttribute("centerLat", centerLat);
        model.addAttribute("centerLng", centerLng);

        return "service/map"; // templates/service/map.html
    }
    @GetMapping("/service/delivery")
    public String delivery() {
        return "service/delivery";
    }
}