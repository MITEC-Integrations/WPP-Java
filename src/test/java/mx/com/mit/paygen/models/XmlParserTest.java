package mx.com.mit.paygen.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import mx.com.mit.paygen.models.PaymentData.FormaPagoType;
import mx.com.mit.paygen.models.UrlData.MonedaType;
import mx.com.mit.paygen.util.XMLHelper;

public class XmlParserTest {
	
	private static final EasyRandom generator = new EasyRandom();

	@Test
	public void thatUrlToXml() throws Exception {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><urlData><reference>eOMtThyhVNLWUZNRcBaQKxI</reference><amount>0.72</amount>"
				+ "<moneda>USD</moneda><canal>W</canal><omitir_notif_default>1</omitir_notif_default><promociones>C,3</promociones><id_promotion>id</id_promotion>"
				+ "<st_correo>0</st_correo><fh_vigencia>18/06/2024</fh_vigencia><mail_cliente>RYtGKbgicZaHCBRQDSx</mail_cliente><prepago>1</prepago></urlData>";
		LocalDate localDate = LocalDate.of(2024, 06, 18);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		UrlData data = UrlData.builder().reference("eOMtThyhVNLWUZNRcBaQKxI").amount(0.72).moneda(MonedaType.USD)
				.omitNotification(1).idPromotion("id").stEmail(0).expirationDate(date)
				.clientEmail("RYtGKbgicZaHCBRQDSx").prepaid(1).build();
		data.setPromotions("C", "3");

		String xml = XMLHelper.format(data, "UTF-8", false);
		assertEquals(expected, xml);
	}

	@Test
	public void thatUrlFromXml() throws Exception {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><urlData><reference>eOMtThyhVNLWUZNRcBaQKxI</reference><amount>0.72</amount>"
				+ "<moneda>USD</moneda><canal>W</canal><omitir_notif_default>1</omitir_notif_default><promociones>C,3,</promociones><id_promotion>id</id_promotion>"
				+ "<st_correo>0</st_correo><fh_vigencia>18/06/2024</fh_vigencia><mail_cliente>RYtGKbgicZaHCBRQDSx</mail_cliente><prepago>1</prepago></urlData>";
		UrlData data = XMLHelper.parse(expected, UrlData.class);
		assertNotNull(data);
		assertEquals(0.72, data.getAmount());

		LocalDate expectedDate = LocalDate.of(2024, 06, 18);
		LocalDate actualDate = data.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		assertEquals(expectedDate, actualDate);
	}

	@Test
	public void thatD3dsToXml() throws Exception {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><d3DSData><ml>mail</ml><cl>phone</cl><dir>address</dir><cd>ciudad</cd><est>ST</est><cp>12345</cp><idc>MEX</idc></d3DSData>";
		D3DSData data = D3DSData.builder().address("address").city("ciudad").countryCode("MEX").email("mail")
				.phone("phone").state("ST").zipCode("12345").build();
		String xml = XMLHelper.format(data, "UTF-8", false);
		assertEquals(expected, xml);
	}

	@Test
	public void thatD3dsFromXml() throws Exception {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><d3DSData><ml>mail</ml><cl>phone</cl><dir>address</dir><cd>ciudad</cd><est>ST</est><cp>12345</cp><idc>MEX</idc></d3DSData>";
		D3DSData data = XMLHelper.parse(expected, D3DSData.class);
		assertNotNull(data);
		assertEquals("phone", data.getPhone());
		assertEquals("address", data.getAddress());
	}

	@Test
	public void thatDatosAdicionalesToXml() throws Exception {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><datos_adicionales><data id=\"1\" display=\"true\"><label>label1</label><value>value1</value></data><data id=\"2\" display=\"false\"><label>label2</label><value>value2</value></data></datos_adicionales>";
		DatosAdicionalesData data = new DatosAdicionalesData();
		data.append(
				DatosAdicionalesData.DataItem.builder().display(true).id(1).label("label1").value("value1").build());
		data.append(
				DatosAdicionalesData.DataItem.builder().display(false).id(2).label("label2").value("value2").build());
		String xml = XMLHelper.format(data, "UTF-8", false);
		assertEquals(expected, xml);
	}

	@Test
	public void thatDatosAdicionalesFromXml() throws Exception {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><datos_adicionales><data id=\"1\" display=\"true\"><label>label1</label><value>value1</value></data><data id=\"2\" display=\"false\"><label>label2</label><value>value2</value></data></datos_adicionales>";
		DatosAdicionalesData data = XMLHelper.parse(expected, DatosAdicionalesData.class);
		assertNotNull(data);
		assertEquals(2, data.getData().size());
		DatosAdicionalesData.DataItem dataItem = data.getData().stream().findFirst().get();
		assertEquals(true, dataItem.getDisplay());
		assertEquals(1, dataItem.getId());
	}

	@Test
	public void thatBusinessToXml() throws Exception {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><businessData><id_company>company</id_company><id_branch>branch</id_branch><user>user</user><pwd>pwd</pwd></businessData>";
		BusinessData data = BusinessData.builder().idBranch("branch").idCompany("company").user("user").pwd("pwd")
				.build();
		String xml = XMLHelper.format(data, "UTF-8", false);
		assertEquals(expected, xml);
	}

	@Test
	public void thatBusinessFromXml() throws Exception {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><businessData><id_company>company</id_company><id_branch>branch</id_branch><user>user</user><pwd>pwd</pwd></businessData>";
		BusinessData data = XMLHelper.parse(expected, BusinessData.class);
		assertNotNull(data);
		assertEquals("company", data.getIdCompany());
		assertEquals("user", data.getUser());
	}
	
	@Test
	public void thatPaymentToXml() throws Exception{
		PaymentData payment = PaymentData.builder()
				.business(generator.nextObject(BusinessData.class))
				.url(generator.nextObject(UrlData.class))
				.paymentMethod(generator.nextObject(FormaPagoType.class))
				.additionalData(generator.nextObject(DatosAdicionalesData.class))
				.data3ds(generator.nextObject(D3DSData.class))
				.build();
		String xml = XMLHelper.format(payment, "UTF-8");
		assertNotNull(xml);
		assertTrue(xml.contains("version"));
	}

}
