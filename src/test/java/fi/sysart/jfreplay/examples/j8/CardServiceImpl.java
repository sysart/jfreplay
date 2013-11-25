package fi.sysart.jfreplay.examples.j8;

import java.util.ArrayList;
import java.util.List;


class CardServiceImpl implements CardService {

    /** actual beef of creating some db restrictions would be here... */
    class CardDbRestrictions implements CardRestrictions<CardRestrictions> {

        private List<String> restrictions = new ArrayList<>();

        @Override
        public CardRestrictions withId(int id) {
            restrictions.add("Id=" + id);
            return this;
        }

        @Override
        public CardRestrictions withTitleLike(String s) {
            restrictions.add("title like '%" + s + '%');
            return this;
        }

        @Override
        public String toString() {
            return restrictions.stream().reduce((s1, s2) -> s1 + ", " + s2).orElse("");
        }
    }

    /** actual beef of performing some entity modifications would be here... */
    class CardEntityModifications implements CardModifications<CardEntityModifications> {

        private List<String> modifications = new ArrayList<>();


        @Override
        public String toString() {
            return modifications.stream().reduce((s1, s2) -> s1 + ", " + s2).orElse("");
        }

        @Override
        public CardEntityModifications changeColor(int rgb) {
            modifications.add("Color =>" + rgb);
            return this;
        }

        @Override
        public CardEntityModifications addTextLine(String line) {
            modifications.add("lines +='" + line + "'");
            return this;
        }

        @Override
        public CardEntityModifications updateTitle(String title) {
            modifications.add("Title => '" + title + "'");
            return this;
        }
    }

    @Override
    public void insert(CardModificationSet modificationSet) {
        CardEntityModifications cardModifications = modificationSet.replay(new CardEntityModifications());
        System.out.println("insert");
        System.out.println("  modifications: " + cardModifications.toString());
    }

    @Override
    public void update(CardCriteriaSet criteriaSet, CardModificationSet modificationSet) {
        CardDbRestrictions dbRestrictions = criteriaSet.replay(new CardDbRestrictions());
        CardEntityModifications cardModifications = modificationSet.replay(new CardEntityModifications());

        System.out.println("update");
        System.out.println("  criteria: " + dbRestrictions.toString());
        System.out.println("  modifications: " + cardModifications.toString());
    }

    @Override
    public void delete(CardCriteriaSet criteriaSet) {
        CardDbRestrictions dbRestrictions = criteriaSet.replay(new CardDbRestrictions());
        System.out.println("delete");
        System.out.println("  criteria: " + dbRestrictions.toString());
    }
}
