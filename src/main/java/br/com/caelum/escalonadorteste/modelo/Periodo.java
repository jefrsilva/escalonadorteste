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

		@Override
		public int getCargaDiaria() {
			return 4;
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

		@Override
		public int getCargaDiaria() {
			return 4;
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

		@Override
		public int getCargaDiaria() {
			return 8;
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

		@Override
		public int getCargaDiaria() {
			return 4;
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

		@Override
		public int getCargaDiaria() {
			return 8;
		}
	};
	
	public abstract LocalTime getHoraDeInicio();
	public abstract LocalTime getHoraDeTermino();
	public abstract int getCargaDiaria();

}
