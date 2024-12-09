package backend.academy.render;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pixel {
    private int r;
    private int g;
    private int b;
    private int hitCount;
}
