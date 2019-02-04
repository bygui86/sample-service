package org.samples.microservices.sample.testingutils;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;


public final class XmlUtils {

	private XmlUtils() {
		// no-op
	}

	public static String toXml(final Object object) throws JAXBException {

		final Writer writer = new StringWriter();
		createMarshaller(InventoryRequestDto.class, false)
				.marshal(object, writer);
		return writer.toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T toObject(final String string) throws JAXBException {

		return (T) createUnmarshaller(InventoryRequestDto.class)
				.unmarshal(
						new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8))
				);
	}

	public static Marshaller createMarshaller(
			final Class<?> xmlObjectClass, final boolean xmlFormattedOutput) throws JAXBException {

		final Marshaller marshaller = JAXBContext
				.newInstance(xmlObjectClass)
				.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, xmlFormattedOutput);
		return marshaller;
	}

	public static Unmarshaller createUnmarshaller(final Class<?> xmlObjectClass) throws JAXBException {

		return JAXBContext
				.newInstance(xmlObjectClass)
				.createUnmarshaller();
	}

}
