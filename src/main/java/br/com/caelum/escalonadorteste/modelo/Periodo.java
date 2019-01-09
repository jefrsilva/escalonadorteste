package br.com.caelum.escalonadorteste.modelo;

import java.time.LocalTime;

public enum Periodo {
	
	MANHA {
		@Override
		public LocalTime getHoraDeInicio() {
			return LocalTime.of(9, 0);
		}

		@Override
		public LocalTime getHoraDeTermino() {
			return LocalTime.of(13, 0);
		}
	},
	
	TARDE {
		@Override
		public LocalTime getHoraDeInicio() {
			return LocalTime.of(14, 0);
		}

		@Override
		public LocalTime getHoraDeTermino() {
			return LocalTime.of(18, 0);
		}
	},
	
	INTEGRAL {
		@Override
		public LocalTime getHoraDeInicio() {
			return LocalTime.of(9, 0);
		}

		@Override
		public LocalTime getHoraDeTermino() {
			return LocalTime.of(17, 0);
		}
	},
	
	NOTURNO {
		@Override
		public LocalTime getHoraDeInicio() {
			return LocalTime.of(19, 0);
		}

		@Override
		public LocalTime getHoraDeTermino() {
			return LocalTime.of(23, 0);
		}
	},
	
	SABADO {
		@Override
		public LocalTime getHoraDeInicio() {
			return LocalTime.of(9, 0);
		}

		@Override
		public LocalTime getHoraDeTermino() {
			return LocalTime.of(17, 0);
		}
	};
	
	public abstract LocalTime getHoraDeInicio();
	public abstract LocalTime getHoraDeTermino();

}
