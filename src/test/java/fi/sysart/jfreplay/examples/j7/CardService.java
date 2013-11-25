package fi.sysart.jfreplay.examples.j7;

import fi.sysart.jfreplay.Replayable;
import fi.sysart.jfreplay.ReplayableFluentProxyFactory;

public interface CardService {

    public interface CardRestrictions<TImpl extends CardRestrictions> {
        TImpl withId(int id);

        TImpl withTitleLike(String s);
    }


    public interface CardModifications<TImpl extends CardModifications> {
        TImpl changeColor(int rgb);

        TImpl addTextLine(String line);

        TImpl updateTitle(String title);
    }

    public interface CardCriteriaSet extends Replayable<CardRestrictions>, CardRestrictions<CardCriteriaSet> {

        public static CardCriteriaSet create() {
            return ReplayableFluentProxyFactory.createProxy(CardCriteriaSet.class);
        }
    }

    public interface CardModificationSet extends Replayable<CardModifications>, CardModifications<CardModificationSet> {

        public static CardModificationSet create() {
            return ReplayableFluentProxyFactory.createProxy(CardModificationSet.class);
        }
    }

    void insert(CardModificationSet modificationSet);

    void update(CardCriteriaSet criteriaSet, CardModificationSet modificationSet);

    void delete(CardCriteriaSet criteriaSet);

}
