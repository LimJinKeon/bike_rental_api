/*
package bike_rental_api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bikes")
public class BikeController {

    private final BikeService service;

    @GetMapping("/nearby")
    public List<BikeStation> getNearby(@RequestParam double lat, @RequestParam double lon, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        System.out.println("Authenticated user: " + username);
        return service.getNearby(lat, lon);
    }
}
*/
