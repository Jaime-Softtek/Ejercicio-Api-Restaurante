package com.helloworld.restaurant.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

@Data
public class Plato {

	public enum Categoria {
		PRIMER_PLATO ("Entrante"),
		SEGUNDO_PLATO ("Plato principal"),
		POSTRE ("Postre");

		private String descripcion;

		Categoria(String descripcion) {
			this.descripcion = descripcion;
		}

		@JsonValue
		public String getDescripcion() {
			return descripcion;
		}

		@JsonCreator
		public static Categoria fromDescripcion(String descripcion) {
			return switch (descripcion) {
				case "Entrante" -> PRIMER_PLATO;
				case "Plato principal" -> SEGUNDO_PLATO;
				case "Postre" -> POSTRE;
				default -> PRIMER_PLATO;
			};
		}

	}

	private final Integer id;
	private final String nombre;
	private final double precio;
	private final Categoria categoria;
	private int calorias;

	public Plato(Integer id, String nombre, double precio, Categoria categoria, int calorias) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.categoria = categoria;
		this.calorias = calorias;
	}



	public static Plato fromPlatoDAO(com.helloworld.restaurant.daos.model.Plato plato) {

		Categoria categoria = switch (plato.categoria()) {
			case 1 -> Categoria.PRIMER_PLATO;
			case 2 -> Categoria.SEGUNDO_PLATO;
			case 3 -> Categoria.POSTRE;
			default -> Categoria.PRIMER_PLATO;
		};

		return new Plato(
				plato.id(),
				plato.nombre(),
				plato.precio(),
				categoria,
				plato.calorias()
		);
	}

	public com.helloworld.restaurant.daos.model.Plato toPlatoDAO() {

		int cat = switch (this.getCategoria()) {
			case PRIMER_PLATO -> 1;
			case SEGUNDO_PLATO -> 2;
			case POSTRE -> 3;
		};

		return new com.helloworld.restaurant.daos.model.Plato(
				this.getId(),
				this.getNombre(),
				this.getPrecio(),
				cat,
				this.getCalorias()
		);
	}

}
