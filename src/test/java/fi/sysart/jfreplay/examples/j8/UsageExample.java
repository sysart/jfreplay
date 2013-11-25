package fi.sysart.jfreplay.examples.j8;

import org.junit.Test;


public class UsageExample {


    @Test
    public void usage_example_demonstrated() {
        /** this example demonstrates inserting updating and deleting cards in a functional and fluent way using
         * jfreplay to convey the search criteria and/or entity modifications */


        CardService service = new CardServiceImpl();// imagine this implementation to be behind a network

        service.insert(mods -> mods.updateTitle("new Card").addTextLine("Hello world!"));

        service.update(
                criteria -> criteria.withId(12),
                mods -> mods.changeColor(444).addTextLine("edited"));

        service.delete(c -> c.withTitleLike("typo"));
    }


}
