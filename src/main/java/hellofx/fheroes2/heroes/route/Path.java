package hellofx.fheroes2.heroes.route;

import hellofx.fheroes2.common.Cursor;
import hellofx.fheroes2.common.H2Size;
import hellofx.fheroes2.common.ListCursor;
import hellofx.fheroes2.heroes.Direction;
import hellofx.fheroes2.heroes.Heroes;
import hellofx.fheroes2.heroes.SkillT;
import hellofx.fheroes2.kingdom.World;
import hellofx.fheroes2.maps.Ground;
import hellofx.fheroes2.maps.objects.Maps;

import java.util.ArrayList;
import java.util.List;


public class Path {
    static PathMap pathPointsMap2 = new PathMap();
    public List<Step> _items = new ArrayList<>();
    public Heroes hero;
    public int dst;
    public boolean hide;

    static int GetPenaltyFromTo(int from, int to, int direct, int pathfinding) {
        int cost1 = Ground.GetPenalty(from, direct, pathfinding); // penalty: for [cur] out
        int cost2 = Ground.GetPenalty(to, Direction.Reflect(direct), pathfinding);
        // penalty: for [tmp] in
        return cost1 + cost2 >> 1;
    }

    public static int GetIndexSprite(int from, int to, int mod) {
        // ICN::ROUTE
        // start index 1, 25, 49, 73, 97, 121 (size arrow path)
        int index = 1;

        switch (mod) {
            case 200:
                index = 121;
                break;
            case 175:
                index = 97;
                break;
            case 150:
                index = 73;
                break;
            case 125:
                index = 49;
                break;
            case 100:
                index = 25;
                break;

            default:
                break;
        }

        switch (from) {
            case Direction.TOP:
                switch (to) {
                    case Direction.TOP:
                        index += 8;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 17;
                        break;
                    case Direction.RIGHT:
                        index += 18;
                        break;
                    case Direction.LEFT:
                        index += 6;
                        break;
                    case Direction.TOP_LEFT:
                        index += 7;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 5;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 19;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.TOP_RIGHT:
                switch (to) {
                    case Direction.TOP:
                        index += 0;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 9;
                        break;
                    case Direction.RIGHT:
                        index += 18;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 19;
                        break;
                    case Direction.TOP_LEFT:
                        index += 7;
                        break;
                    case Direction.BOTTOM:
                        index += 20;
                        break;
                    case Direction.LEFT:
                        index += 6;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.RIGHT:
                switch (to) {
                    case Direction.TOP:
                        index += 0;
                        break;
                    case Direction.BOTTOM:
                        index += 20;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 19;
                        break;
                    case Direction.RIGHT:
                        index += 10;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 1;
                        break;
                    case Direction.TOP_LEFT:
                        index += 7;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 21;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.BOTTOM_RIGHT:
                switch (to) {
                    case Direction.TOP_RIGHT:
                        index += 1;
                        break;
                    case Direction.RIGHT:
                        index += 2;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 11;
                        break;
                    case Direction.BOTTOM:
                        index += 20;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 21;
                        break;
                    case Direction.TOP:
                        index += 0;
                        break;
                    case Direction.LEFT:
                        index += 22;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.BOTTOM:
                switch (to) {
                    case Direction.RIGHT:
                        index += 2;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 3;
                        break;
                    case Direction.BOTTOM:
                        index += 12;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 21;
                        break;
                    case Direction.LEFT:
                        index += 22;
                        break;
                    case Direction.TOP_LEFT:
                        index += 16;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 1;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.BOTTOM_LEFT:
                switch (to) {
                    case Direction.BOTTOM_RIGHT:
                        index += 3;
                        break;
                    case Direction.BOTTOM:
                        index += 4;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 13;
                        break;
                    case Direction.LEFT:
                        index += 22;
                        break;
                    case Direction.TOP_LEFT:
                        index += 23;
                        break;
                    case Direction.TOP:
                        index += 16;
                        break;
                    case Direction.RIGHT:
                        index += 2;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.LEFT:
                switch (to) {
                    case Direction.TOP:
                        index += 16;
                        break;
                    case Direction.BOTTOM:
                        index += 4;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 5;
                        break;
                    case Direction.LEFT:
                        index += 14;
                        break;
                    case Direction.TOP_LEFT:
                        index += 23;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 17;
                        break;
                    case Direction.BOTTOM_RIGHT:
                        index += 3;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            case Direction.TOP_LEFT:
                switch (to) {
                    case Direction.TOP:
                        index += 16;
                        break;
                    case Direction.TOP_RIGHT:
                        index += 17;
                        break;
                    case Direction.BOTTOM_LEFT:
                        index += 5;
                        break;
                    case Direction.LEFT:
                        index += 6;
                        break;
                    case Direction.TOP_LEFT:
                        index += 15;
                        break;
                    case Direction.BOTTOM:
                        index += 4;
                        break;
                    case Direction.RIGHT:
                        index += 18;
                        break;
                    default:
                        index = 0;
                        break;
                }
                break;

            default:
                index = 0;
                break;
        }

        return index;
    }

    public boolean Find(int to, int limit) {
        int pathfinding = hero.GetLevelSkill(SkillT.PATHFINDING);
        int from = hero.mapPosition.GetIndex();

        int cur = from;
        int alt = 0;
        int tmp = 0;

        pathPointsMap2.clear();
        //TODO
        //var it1_2 = pathPointsMap2._rows.begin();
        //var it2_2 = pathPointsMap2._rows.end();

        cell_t currCell = pathPointsMap2.get(cur);
        currCell.cost_g = 0;
        currCell.cost_t = 0;
        currCell.parent = -1;
        currCell.open = 0;

        var directions = Direction.All();
        _items.clear();
        var world = World.Instance;
        H2Size wSize = new H2Size(world.w, world.h);
        while (cur != to) {
            for (var direction : directions) {
                if (!Maps.isValidDirection(cur, direction, wSize))
                    continue;
                tmp = Maps.GetDirectionIndex(cur, direction);
                cell_t tmpItem2 = pathPointsMap2.get(tmp);
                var curItem2 = pathPointsMap2.get(cur);

                if (tmpItem2.open == 0) continue;
                int costg = GetPenaltyFromTo(cur, tmp, direction, pathfinding);

                // new
                if (-1 == tmpItem2.parent) {
                    if (0 != (curItem2.passbl & direction) ||
                            PassableFromToTile(hero, cur, tmp, direction, to)) {
                        curItem2.passbl |= direction;

                        tmpItem2.direct = direction;
                        tmpItem2.cost_g = costg;
                        tmpItem2.parent = cur;
                        tmpItem2.open = 1;
                        tmpItem2.cost_d = 50 * Maps.GetApproximateDistance(tmp, to);
                        tmpItem2.cost_t = curItem2.cost_t + costg;

                        curItem2.passbl |= direction;

                        tmpItem2.direct = direction;
                        tmpItem2.cost_g = costg;
                        tmpItem2.parent = cur;
                        tmpItem2.open = 1;
                        tmpItem2.cost_d = 50 * Maps.GetApproximateDistance(tmp, to);
                        tmpItem2.cost_t = curItem2.cost_t + costg;
                    }
                }
                // check alt
                else {
                    if (tmpItem2.cost_t > curItem2.cost_t + costg &&
                            (0 != (curItem2.passbl & direction) || PassableFromToTile(hero, cur, tmp, direction, to))) {
                        curItem2.passbl |= direction;

                        tmpItem2.direct = direction;
                        tmpItem2.parent = cur;
                        tmpItem2.cost_g = costg;
                        tmpItem2.cost_t = curItem2.cost_t + costg;
                    }
                }
            }


            pathPointsMap2.get(cur).open = 0;
/*
//TODO
            it1_2 = pathPointsMap2._rows.begin();
            alt = -1;
            tmp = MAXU16;

            // find minimal cost
            it2_2 = pathPointsMap2._rows.end();
            for (; it1_2 != it2_2; ++it1_2)
                if ((*it1_2).Value.open)
            {
                 cell_t cell2 = (*it1_2).Value;

                if (cell2.cost_t + cell2.cost_d < tmp)
                {
                    tmp = cell2.cost_t + cell2.cost_d;
                    alt = (*it1_2).Key;
                }
            }
            // not found, and exception
            if (MAXU16 == tmp || -1 == alt) break;

            cur = alt;

            //if (0 < limit && GetCurrentLength(pathPointsMap, cur) > limit) break;
            if (0 < limit && GetCurrentLength(pathPointsMap2, cur) > limit) break;

 */
        }

        // save path
        if (cur == to) {
            while (cur != from) {
                var curItem2 = pathPointsMap2.get(cur);
                push_front(new Step(curItem2.parent, curItem2.direct, curItem2.cost_g));
                cur = curItem2.parent;
            }
        }
        return !empty();
    }

    private void push_front(Step step) {
        _items.add(0, step);
    }

    private boolean empty() {
        return size() == 0;
    }

    private boolean PassableFromToTile(Heroes hero, int from, int to, int direct, int dst) {
        //TODO
        return false;
    }

    public boolean isShow() {
        return !hide;
    }

    public ListCursor<Step> begin() {
        return new ListCursor<>(_items);
    }

    public ListCursor<Step> end() {
        return Cursor.end(_items);
    }

    public int GetAllowStep() {
        var green = 0;
        var move_point = hero.GetMovePoints();

        for (var it : _items) {
            if (move_point < it.GetPenalty())
                break;
            move_point -= it.GetPenalty();
            ++green;
        }

        return green;
    }

    public int size() {
        return _items.size();
    }

    public Step get(int index) {
        return _items.get(index);
    }

    public boolean isValid() {
        return size() != 0;
    }

    public int GetFrontPenalty() {
        return empty() ? 0 : get(0).GetPenalty();
    }
}
