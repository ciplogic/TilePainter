package hellofx.fheroes2.heroes;

import java.util.ArrayList;
import java.util.List;

public class SecSkills {
    public List<Secondary> _items = new ArrayList<>();

    public void AddSkill(Secondary skill) {
        //TODO: look for already existing skill
        _items.add(skill);
    }
}
