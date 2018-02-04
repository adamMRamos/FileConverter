package com.dev.rapture.test;
import com.dev.rapture.fileparser.ParsedDataObservable;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * Created by amram on 2/4/2018.
 */
public class ParsedObservableTest
{
    private String[] data = new String[]{"a","b","c"};
    private ParsedDataObservable po;

    @Before
    public void setUpObservable()
    {
        po = new ParsedDataObservable();
    }

    @Test
    public void hasDataTest()
    {
        po.pushData(data);
        assertTrue(po.hasData());
    }

    @Test
    public void doesNotHaveDataTest()
    {
        po.pushData(data);
        po.pullData();
        assertFalse(po.hasData());
    }

    @Test
    public void hasChanged()
    {
        po.pushData(data);
        assertTrue(po.hasChanged());
    }

    @Test
    public void hasNotChanged()
    {
        po.pullData();
        assertFalse(po.hasChanged());
    }
}
