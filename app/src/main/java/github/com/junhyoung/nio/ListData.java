package github.com.junhyoung.nio;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by 준형 on 2016-02-16.
 */
public class ListData {
    int id;
    public String text;

    public String date;

    /**
     * 날짜순으로 정렬
     */
    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData mListDate_1, ListData mListDate_2) {
            return sCollator.compare(mListDate_1.date, mListDate_2.date);
        }
    };
}
