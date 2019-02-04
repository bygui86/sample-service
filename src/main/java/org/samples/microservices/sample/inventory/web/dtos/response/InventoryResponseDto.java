package org.samples.microservices.sample.inventory.web.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
// PLEASE NOTE: IntelliJ IDEA is not able to recognise the attribute "onMethod" for the Getter annotation
// see https://github.com/rzwitserloot/lombok/issues/1354
// @Setter(value = AccessLevel.PUBLIC, onMethod = @__({@XmlElement(name = "inventoryWriteDto")}))
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "inventories")
public class InventoryResponseDto {

	@NotNull
	@Size(min = 1)
	List<@NotNull @Valid InventoryWriteResponseDto> responses;

	@XmlElement(name = "inventory")
	public void setResponses(final List<InventoryWriteResponseDto> responses) {

		this.responses = responses;
	}

	@Override
	public boolean equals(final Object other) {

		if (!(other instanceof InventoryResponseDto)) {
			return false;
		}

		if (this == other) {
			return true;
		}

		final InventoryResponseDto otherDto = (InventoryResponseDto) other;

		return getResponses().size() == otherDto.getResponses().size()
				&& new HashSet<>(getResponses()).equals(new HashSet<>(otherDto.getResponses()));
	}

	/**
	 * Default implementation coming from Java doc
	 * See https://docs.oracle.com/javase/1.5.0/docs/api/java/util/List.html#hashCode%28%29
	 */
	@Override
	public int hashCode() {

		int hashCode = 1;
		for (final InventoryWriteResponseDto responseDto : getResponses()) {
			hashCode = 31 * hashCode + (responseDto == null ? 0 : responseDto.hashCode());
		}
		return hashCode;
	}

}
