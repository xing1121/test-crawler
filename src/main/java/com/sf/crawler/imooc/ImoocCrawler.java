package com.sf.crawler.imooc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.sf.crawler.pojo.Course;
import com.sf.crawler.util.HttpClientUtils;

public class ImoocCrawler {
    
	private static final String BaseUrl = "https://www.imooc.com/course/list?c=java&page={page}";

    public static void main(String[] args) throws Exception {
        //定义爬虫入口
        String startUrl = StringUtils.replace(BaseUrl, "{page}", "1");
        String html = HttpClientUtils.doGet(startUrl).getContent();
        
        //解释html
        Document document = Jsoup.parse(html);
        
        //获取总页数
        String totalPageStr = document.select(".pager-total").text();
        Integer totalPage = Integer.parseInt(totalPageStr);
        StringBuffer sb = new StringBuffer();
        List<Course> list = new ArrayList<>(200);
        
        for (int i = 1; i <= totalPage; i++) {
        	
            String requestUrl = StringUtils.replace(BaseUrl, "{page}", i+"");
            String string = HttpClientUtils.doGet(requestUrl).getContent();
            Document document2 = Jsoup.parse(string);
            
            //获取课程列表
            Elements lis = document2.select(".course-list .course-card-container");
            
            for (Element li : lis) {
            	Course course = new Course();
            	String href = li.child(0).attr("href");
            	
            	// id
            	Long id = Long.parseLong(href.replace("/learn/", ""));
            	course.setId(id);
            	
            	// url
            	String url = "https://www.imooc.com" + href;
            	course.setUrl(url);
            	
            	// 图片
                Element imgElement = li.select(".course-card-top").get(0).child(0);
                String img = imgElement.attr("src").replace("//", "");
                course.setImg(img);
                
                // 主题
                Element titleElement = li.select(" .course-card-content").get(0).child(0);
                String title = titleElement.text();
                course.setTitle(title);
                
                // 简介
                Element ele = li.select(" .course-card-content .clearfix.course-card-bottom").get(0);
                String introduce = ele.select(" p.course-card-desc").first().text();
                course.setIntroduce(introduce);
                
                // 级别
                String level = ele.select(" .course-card-info").first().child(0).text();
                course.setLevel(level);
                
                // 热度
                Integer hot = Integer.parseInt(ele.select(" .course-card-info").first().child(1).text());
                course.setHot(hot);
                
                list.add(course);
            }

        }
        
        list = list.stream().sorted((x1, x2)->{
        	return -(x1.getHot().compareTo(x2.getHot()));
        }).collect(Collectors.toList());
        
        for (Course course : list) {
        	sb.append(JSON.toJSONString(course)+"\n");
        }
      
        //持久化
        FileUtils.writeStringToFile(new File("D:\\user\\80002888\\桌面\\products.txt"), sb.toString());
        System.out.println("完毕！");
    }

}
