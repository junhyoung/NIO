package github.com.junhyoung.nio;

import java.text.Collator;
import java.util.Comparator;

public class ListCall {
    int id;
    public String name;
    public String number;


    /**
     * 날짜순으로 정렬
     */
    public static final Comparator<ListCall> ALPHA_COMPARATOR = new Comparator<ListCall>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListCall mListDate_1, ListCall mListDate_2) {
            return sCollator.compare(mListDate_1.name, mListDate_2.name);
        }
    };
}