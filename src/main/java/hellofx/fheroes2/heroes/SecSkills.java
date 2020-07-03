package hellofx.fheroes2.heroes;

import java.util.ArrayList;
import java.util.List;

public class SecSkills {
    public List<Secondary> _items = new ArrayList<>();

    public SecSkills(int race) {
        //TODO
    }

    public SecSkills() {
    }

    public void AddSkill(Secondary skill) {
        //TODO: look for already existing skill
        _items.add(skill);
    }

    public int GetValues(int skill) {
        //TODO
        return 0;
    }
}
