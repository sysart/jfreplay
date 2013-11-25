package fi.sysart.jfreplay.examples.j7;

import org.junit.Test;


public class UsageExample {


    @Test
    public void usage_example_demonstrated() {
        /** this example demonstrates inserting updating and deleting cards in a functional and fluent way using
         * jfreplay to convey the search criteria and/or entity modifications */


        CardService service = new CardServiceImpl();// imagine this implementation to be behind a network

        service.insert(
                CardService.CardModificationSet.create().updateTitle("new Card").addTextLine("Hello world!"));

        service.update(
                CardService.CardCriteriaSet.create().withId(12),
                CardService.CardModificationSet.create().changeColor(444).addTextLine("edited"));

        service.delete(CardService.CardCriteriaSet.create().withTitleLike("typo"));
    }


}
