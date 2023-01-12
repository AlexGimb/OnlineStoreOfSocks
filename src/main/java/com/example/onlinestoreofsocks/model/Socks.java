package com.example.onlinestoreofsocks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.onlinestoreofsocks.service.Color;
import com.example.onlinestoreofsocks.service.Size;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {
    private  Color colors;
    private  Size size;
    @Min(1)
    @Max(100)
    private int cottonPart;
    @Min(1)
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cottonPart == socks.cottonPart && colors == socks.colors && size == socks.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colors, size, cottonPart);
    }
}
