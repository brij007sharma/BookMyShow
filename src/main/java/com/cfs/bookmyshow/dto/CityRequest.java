package com.cfs.bookmyshow.dto;

import com.cfs.bookmyshow.entity.City;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityRequest {
    private String name;
    private String state;
    private String country;

    public City toCity() {
        City city = new City();
        city.setName(this.name);
        city.setState(this.state);
        return city;
    }

    public void updateCity(City city) {
        city.setName(this.name);
        city.setState(this.state);
    }
}
