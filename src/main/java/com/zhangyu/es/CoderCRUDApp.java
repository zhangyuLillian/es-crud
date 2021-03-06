package com.zhangyu.es;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhangyu
 * @description 编程者CRUD应用程序
 * @date 2019/3/28 9:27
 */
public class CoderCRUDApp {
    private static TransportClient client;

    public static void main(String[] args) {
        try {
            client = initClient();
            //新增
            //createCoder(client);
            //修改
            //updateCoder(client);
            //查询
            //getCoder(client);
            //删除
            //deleteCoder(client);
            //复杂条件查询
            searchCoders(client);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //记得关闭client
            client.close();
        }

    }

    //构建Client
    private static TransportClient initClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                            .put("cluster.name","elasticsearch")
                            .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                                 .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9300));
        return client;
    }


    /**
     * 新增一个coder (创建一个document)
     * @param client
     * @throws IOException
     */
    private static void createCoder(TransportClient client) throws IOException {
        IndexResponse response = client.prepareIndex("company","coder","4")
                                       .setSource(XContentFactory.jsonBuilder()
                                               .startObject()
                                                    .field("name","李")
                                                    .field("age",28)
                                                    .field("country","china")
                                                    .field("salary",17000)
                                                    .field("language","java")
                                                    .field("join_date","2018-3-1")
                                               .endObject())
                                       .get();
        System.out.println(response.getResult());
    }

    /**
     * 修改coder信息
     * @param client
     * @throws IOException
     */
    private static void updateCoder(TransportClient client) throws IOException {
        UpdateResponse response = client.prepareUpdate("company","coder","1")
                                        .setDoc(XContentFactory.jsonBuilder()
                                            .startObject()
                                                .field("salary",20000)
                                            .endObject())
                                        .get();
        System.out.println(response.getResult());
    }

    /**
     * 获取一个coder
     * @param client
     */
    private static void getCoder(TransportClient client) {
        GetResponse response = client.prepareGet("company","coder","1").get();
        System.out.println(response.getSourceAsString());
    }

    /**
     * 需求：
     * 1、语言是java
     * 2、薪水在15k-20k
     * 3、分页查询 查找第一页
     * @param client
     */
    private static void searchCoders(TransportClient client) {
        SearchResponse response = client.prepareSearch("company")
                                        .setTypes("coder")
                                        .setQuery(QueryBuilders.matchQuery("language","java"))
                                        .setPostFilter(QueryBuilders.rangeQuery("salary").from(13000).to(25000))
                                        .setFrom(0).setSize(5)
                                    .get();
        SearchHit[] searchHits = response.getHits().getHits();
        for (int i = 0;i < searchHits.length;i ++) {
            System.out.println(searchHits[i].getSourceAsString());
        }
    }

    /**
     * 删除coder
     * @param client
     */
    private static void deleteCoder(TransportClient client) {
        DeleteResponse response = client.prepareDelete("company","coder","1").get();
        System.out.println(response.getResult());
    }
}
