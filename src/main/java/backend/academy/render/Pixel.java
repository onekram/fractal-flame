package backend.academy.render;

import backend.academy.transformation.LinearTransformation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Pixel {
    private int red;
    private int green;
    private int blue;
    private int hitCount;

    public synchronized void paint(LinearTransformation transformation) {
        if (hitCount() == 0) {
            red(transformation.red());
            green(transformation.green());
            blue(transformation.blue());
            hitCount(1);
        } else {
            red((red() + transformation.red()) / 2);
            green((green() + transformation.green()) / 2);
            blue((blue() + transformation.blue()) / 2);
            hitCount(hitCount() + 1);
        }
    }
}
