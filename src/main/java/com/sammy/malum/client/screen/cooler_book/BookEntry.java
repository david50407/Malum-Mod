package com.sammy.malum.client.screen.cooler_book;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.screen.cooler_book.BookEntry.EntryLine.LineEnum;
import com.sammy.malum.client.screen.cooler_book.pages.BookPage;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;

import static com.sammy.malum.client.screen.cooler_book.BookEntry.EntryLine.LineEnum.*;

public class BookEntry
{
    public final ItemStack iconStack;
    public final String identifier;
    public final int xOffset;
    public final int yOffset;
    public ArrayList<BookPage> pages = new ArrayList<>();
    public BookEntry(String identifier, Item item, int xOffset, int yOffset)
    {
        this.iconStack = item.getDefaultInstance();
        this.identifier = identifier;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    public String translationKey()
    {
        return "malum.gui.book.entry." + identifier;
    }
    public String descriptionTranslationKey()
    {
        return "malum.gui.book.entry." + identifier + ".description";
    }
    public BookEntry addPage(BookPage page)
    {
        pages.add(page);
        return this;
    }
    public BookEntry addModCompatPage(BookPage page, String modId)
    {
        if (ModList.get().isLoaded(modId))
        {
            pages.add(page);
        }
        return this;
    }
    public static class EntryLine
    {
        public enum LineEnum
        {
            HORIZONTAL, VERTICAL,

            BEND_UP_LEFT, BEND_UP_RIGHT,
            BEND_DOWN_LEFT, BEND_DOWN_RIGHT,

            TWO_WAY_BEND_UP, TWO_WAY_BEND_LEFT,
            TWO_WAY_BEND_RIGHT, TWO_WAY_BEND_DOWN,

            CONNECTION_UP, CONNECTION_DOWN,
            CONNECTION_LEFT, CONNECTION_RIGHT
        }
        public final int xOffset;
        public final int yOffset;
        public ArrayList<LineEnum> lines;

        public EntryLine(int xOffset, int yOffset, LineEnum... arrows)
        {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.lines = MalumHelper.toArrayList(arrows);
        }
    }
}