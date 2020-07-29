package com.heo.dailycommit.parse;

import com.heo.dailycommit.utils.Utils;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HtmlParse {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        // jsoup document에서 날짜를 역순으로 가서 연속 커밋일수 확인
        // 매일 아침에 계산
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
        public int getAllCountRecursive(Document doc, String date, int recurCount) {
            String str = doc.body().select("[data-date=\"" + date + "\"]").attr("data-count");

            if (str.isEmpty())
                return 0;

            int count = Integer.parseInt(str);

            if (count > 0) {
                return 1 + getAllCountRecursive(doc, Utils.getDate("yyyy-MM-dd", ++recurCount), recurCount);
            } else {
                return 0 + getAllCountRecursive(doc, Utils.getDate("yyyy-MM-dd", ++recurCount), recurCount);
            }
        }

        public int getAllContributionCount(Document doc){
            int count = doc.body().select("rect").size();
            
            return count;
        }

        
}