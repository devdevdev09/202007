package com.heo.dailycommit.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heo.dailycommit.utils.Utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class HtmlParse {
        // private final Logger logger = LoggerFactory.getLogger(this.getClass());

        // jsoup document에서 날짜를 역순으로 가서 연속 커밋일수 확인
        public int getCountRecursive(Document doc, String date, int recurCount) {
            String str = doc.body().select("[data-date=\"" + date + "\"]").attr("data-count");

            if (str.isEmpty())
                return 0;

            int count = Integer.parseInt(str);

            if (count > 0) {
                return 1 + getCountRecursive(doc, Utils.getDate("yyyy-MM-dd", ++recurCount), recurCount);
            } else {
                return 0;
            }
        }

        public int getCountByDoc(Document doc, String date) {
            String count = doc.body().select("[data-date=\"" + date + "\"]").attr("data-count");
    
            if(count.isEmpty()){
                return 0;
            }else{
                return Integer.parseInt(count);
            }
            
        }

        // jsoup document에서 모든 커밋 일수 계산
        public int getLastYearCountRecursive(Document doc, String date, int recurCount) {
            String str = doc.body().select("[data-date=\"" + date + "\"]").attr("data-count");

            if (str.isEmpty())
                return 0;

            int count = Integer.parseInt(str);

            if (count > 0) {
                return 1 + getLastYearCountRecursive(doc, Utils.getDate("yyyy-MM-dd", ++recurCount), recurCount);
            } else {
                return 0 + getLastYearCountRecursive(doc, Utils.getDate("yyyy-MM-dd", ++recurCount), recurCount);
            }
        }

        public int getAllContributionCount(Document doc){
            int count = doc.body().select("rect").size();
            
            return count;
        }

        public List<Map<String, String>> getYearList(Document doc){
            List<Map<String, String>> result = new ArrayList<Map<String,String>>();
            
            Elements elements = doc.body().select("ul.filter-list > li > a");
            
            for(Element element : elements) {
                Map<String, String> item = new HashMap<String, String>();
                item.put("year", element.text());
                item.put("href", element.attr("href"));

                result.add(item);
            }

            return result;
        }
}