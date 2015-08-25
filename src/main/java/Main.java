import org.apache.commons.io.IOUtils;
import play.Mode;
import play.mvc.Controller;
import play.routing.RoutingDsl;
import play.server.Server;

public class Main extends Controller {

    public static final String CATEGORY_FIXTURES_PATH = "fixtures/categories/all.json";

    public static void main(String[] args) throws Exception {
        Server authServer = Server.forRouter(new RoutingDsl()
                        .POST("/oauth/token").routeTo(() -> {
                                    final String projectKey = request().getQueryString("scope").replace("manage_project:", "");
                                    return ok(String.format("{\"access_token\":\"U-3HAn2JRclngB5xOTHMB-4SQUvOrC54\",\"token_type\":\"Bearer\",\"expires_in\":172800,\"scope\":\"manage_project:%s\"}", projectKey));
                                }
                        )
                        .build(),
                Mode.PROD, 19000
        );

        Server apiServer = Server.forRouter(new RoutingDsl()
                        .GET("/:projectKey/categories").routeTo(to -> {
                                    return ok(stringFromResource(CATEGORY_FIXTURES_PATH));
                                }
                        )
                        .build(),
                Mode.PROD, 19001
        );
    }

    public static String stringFromResource(final String resourcePath) throws Exception {
        return IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath), "UTF-8");
    }
}
