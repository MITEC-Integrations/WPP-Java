# SDK Pagos Online

SDK Pagos Online es un conjunto de clases Java que facilitan la integracion al generador de ligas.

## Instalación

Para compilar y dar mantenimiento al proyecto

```xml
<dependency>
    <groupId>mx.com.mit</groupId>
    <artifactId>wpp-java-sdk</artifactId>
    <version>0.0.1</version>
</dependency>

```

## Estructura del proyecto.

**WppClient** es la clase encargada de armar y procesar la petición para la generacion de ligas.

El paquete **models** tiene los objetos para generar la cadena XML.

El folder **validators** incluye reglas de validación básicas requeridas para determinar si el objeto puede ser enviado al generador de ligas. 
La documentación de los datos requeridos se pueden encontrar en el sandbox de [Pagos Online](https://sandboxpol.mit.com.mx/generar).

## Construcción del proyecto.
Para compilar el proyecto y generar el artefacto que será usado por los integradores solo es necesario ejecutar `mvn install`, al finalizar la ejecución se creará la carpeta **target/** que contienen todos los elementos que se publicaran en el *repositorio maven*.

## Publicación
Para publicar el artefacto solo será necesario ubicarse en la carpeta *dist/types* (o dist/cjs), autenticarse en npm y subir el modulo.

```bash
mvn deploy
```

## Uso
El modulo esta implementado para trabajar con *JAVA 11* o superior.

Debe declarar un objeto de tipo *PaymentData.build()* y capturar los datos proporcionados por **MIT**. Posteriormente, se crea una instancia de *WppClient* proporcionando el *endpoint*, *identificador de pagos* y *llave de cifrado* en **hexadecimal**



```java
import mx.com.mit.paygen.models.*;
import mx.com.mit.paygen.WppClient;

public class WppClientTest {

    public static void main(String[] args) throws Exception {
    UrlData urlData = UrlData.builder().reference("reference001").amount(10.0).moneda(MonedaType.MXN)
        .promotions("C").stEmail(1).prepaid(0).omitNotification(1).build();

    PaymentData payment = PaymentData.builder().business(BusinessData.builder().idBranch("01SNBXBRNCH")
            .idCompany("SNBX").user("SNBXUSR0123").pwd("SECRETO").build()).url(urlData).build();

    WppClient client = new WppClient(new URL("ENDPOINT"), "PagosID", "PagosKey");
    URL url = client.getUrlPayment(payment);
    System.out.println(url);
  }
}
```

## Webhook o Http Callback
El **comercio** debe exponer un *http callback* o *URI endpoint* que le permita conocer si el pago del cliente fue **aprobado** o **declinado** y aplicar la lógica correspondiente a su negocio.

Para descifrar el mensaje, se puede utilizar el método `processAfterPaymentResponse` de la clase `WppClient`

```java
    const response = request.getParameter("strResponse");
    PaymentResponse body = client.processAfterPaymentResponse(response);
}
afterPayment();
```

## License
[MIT](https://choosealicense.com/licenses/mit/)