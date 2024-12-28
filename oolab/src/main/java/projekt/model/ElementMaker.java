package projekt.model;

import java.util.HashMap;

public interface ElementMaker<T> {
    T make(Vector2d position);
}
