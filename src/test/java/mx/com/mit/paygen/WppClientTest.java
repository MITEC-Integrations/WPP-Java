package mx.com.mit.paygen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mx.com.mit.paygen.models.BusinessData;
import mx.com.mit.paygen.models.PaymentData;
import mx.com.mit.paygen.models.PaymentResponse;
import mx.com.mit.paygen.models.UrlData;
import mx.com.mit.paygen.models.UrlData.MonedaType;

public class WppClientTest {

	private static EasyRandom generator = new EasyRandom();
	private static WppClient client;

	@BeforeAll
	public static void init() throws Exception {
		client = new WppClient(new URL("https://sandboxpo.mit.com.mx/gen"), "SNDBX123",
				"5DCC67393750523CD165F17E1EFADD21");
		generator = new EasyRandom();
	}

	@Test
	public void thatUrlPaymentIsGenerated() throws Exception {
		UrlData urlData = UrlData.builder().reference("reference001").amount(10.0).moneda(MonedaType.MXN)
				.promotions("C").stEmail(1).prepaid(0).omitNotification(1).build();

		PaymentData payment = PaymentData.builder().business(BusinessData.builder().idBranch("01SNBXBRNCH")
				.idCompany("SNBX").user("SNBXUSR0123").pwd("SECRETO").build()).url(urlData).build();

		URL url = client.getUrlPayment(payment);
		assertNotNull(url);
		assertEquals(url.getHost(), "sandboxpol.mit.com.mx");
	}

	@Test
	public void thatDecipherRespose() {
		PaymentResponse r = client.processAfterPaymentResponse(
				"UHkxa6LtFWnN45zIb1dPCIcLSR3WOHmM2wejZIIiyhmQOOFoKA2iJnKyXLa5Y/2FnqoiVH1XV7tM5Uhzw6W3v0xzfNfnKfee4oFqpLxHG1uCqxgYAbCI7eWT37Im7VVCzpxl3QSPiXRQGnKrLOdNjaART1omE8/lUvKSgOC8mVnBg0VD1bSwOUJl8PqfzAM9fwiboQ7ucGxF7JTTmt0hdN2Fz58MXpWYEWcGJDUE9TMNCbUCACO3S1u9e10bCDQa4oSZ0vFbp5NZVivk8Vbp2jqo6LFLQYt5nZKqdGOdfusg4rVYD2oDrvSV3njkI9onpxsAU4iJT4L3ebsqyXcHxaAmu/T/B1m+WoSphFeV5Wcla8R6vh0M2dZFjDX6X016Dr7JR25aulgy1TmpzM0PEOxHGcsl+iGHk17dNECZ8/kY9Vsf9R4sz0sFswjQb3QOuawCMUPht4pdKsN1RXioTvLyIQpAKAavuGOIF+YAg59KWHmFFFdcLmeNyppyuY5RiguhfQVk8bql4Zd8sDOv/nomrdLWyxYEKUdjtkeKcz5EeyAkLUDNXsHrmOFn6MoF9ThSWB9eVHIlvIdkuaW5Qy3r7juXjNMcBW4suq2LiEvs+DbwhGSoKYAd2WhrMtt6fST2/kiYdbZT3A8EyN4Tz/6Qh2KJ4ur/hfiZnkyV/r4R3Rmxm+FYJpbPatItHBQxWx/H9BUtXQ8BO3j2h7jcai5XwmmXSQAgvI+GvL/P6oO3h51dnrRfhVUfOjV0BF0Yiz4GJlvxrWoI8u/s2bIi4LEwjQ7CMJzmUCeCVIPro730JLhkYFNZsvV/ANCt1sNj7HS0olWO3QZdmX7cLK6gXLMO4b/LMAoVqmOW95Nk+PuTHp+TOtOL8FIUNCVEqpXB+kHgIVTN0GdCsBKgvVrmWi5IA8BSKPfX33e8+XWQ2KHJJ4VvH8yyDb2Akxuc8XoxIUoXtV1u0GG7icQVaNEV/Xmj9ofUTqDcGaqqYN4OsZZ7t0ruYMAYwQQw7Y7/OGhHJtmLba+MfGUhpTeSCuxW80AWTXftq2k9kDR0gxSacEufIOfVuhGtCf5eZqxTawk8RJ4hArxDiFS2ODDvHexOk8iEzkz5rysdBTi1cgPTq4w=");
		assertNotNull(r);
		assertEquals("approved", r.getResponse());
	}
}
