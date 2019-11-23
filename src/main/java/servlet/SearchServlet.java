package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Post;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private ObjectMapper objectMapper;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Post post = new Post(1L,"Such a sad name","Text contains 56",
//                "Nowhere", LocalDateTime.now(),false);
//        Post post1 = new Post(2L,"Such a good name","Мейоз",
//                "NoNoNo", LocalDateTime.now(),false);
//        String jsonValue = objectMapper.writeValueAsString(post);
//        String value2 = objectMapper.writeValueAsString(post1);
//        System.out.println(jsonValue);
//        System.out.println(value2);
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

// on shutdown
       // MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();

//        MatchPhraseQueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("name","such a name");
//        queryBuilder.slop(3);
        WildcardQueryBuilder qb = QueryBuilders.wildcardQuery("name", "*ad");
        SearchResponse scrollResp = client.prepareSearch("test_index")
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))
                .setQuery(qb)
                .setSize(100).get(); //max of 100 hits will be returned for each scroll
//Scroll until no hits are returned
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                System.out.println(hit.toString());
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        } while(scrollResp.getHits().getHits().length != 0);
//        IndexResponse response = client.prepareIndex("test_index", "_doc", String.valueOf(post1.getId()))
//                .setSource(value2, XContentType.JSON)
//                .get();
//        System.out.println(response)
//        response = client.prepareIndex("test_index", "_doc", String.valueOf(post.getId()))
//                .setSource(jsonValue, XContentType.JSON)
//                .get();
//        System.out.println(response);
        client.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        this.objectMapper = new ObjectMapper();
    }
}
