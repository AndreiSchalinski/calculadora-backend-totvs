import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class App {
    public static void main(String[] args) throws Exception {

        LocalDate dataInicio = LocalDate.of(2024, 01, 01);

        LocalDate dataFim = LocalDate.of(2034, 01, 01);

        BigDecimal totalEmprestimo = new BigDecimal("140000.00");

        int totalMeses = calculaIntervaloTotalMeses(dataInicio, dataFim);

        // ---------------------Inputs de form front-end acima-----------------------

        BigDecimal principalAmortizacao = totalEmprestimo.divide(new BigDecimal(totalMeses), 10, RoundingMode.HALF_UP);

        BigDecimal principalSaldo = new BigDecimal("140000.00");
        
        int indice = 1;

        for (int parcela = 1; parcela <= totalMeses; parcela++) {

            if (parcela > 1)
                indice = 15;

            LocalDate primeiroDiaMes = dataInicio.withDayOfMonth(indice);
            LocalDate ultimoDiaMes = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth());

            BigDecimal amortizacaoAtual = principalAmortizacao;

            if (parcela == totalMeses) {
                amortizacaoAtual = principalSaldo;
            }

            principalSaldo = calculaPrincipalSaldo(parcela, principalSaldo, amortizacaoAtual);

            imprimirTabela(parcela, primeiroDiaMes, ultimoDiaMes, amortizacaoAtual, principalSaldo.setScale(2, RoundingMode.HALF_UP));

            dataInicio = dataInicio.plusMonths(1);
        }

    }

    public static int calculaIntervaloTotalMeses(LocalDate dataInicial, LocalDate dataFinal) {
        return (int) ChronoUnit.MONTHS.between(dataInicial, dataFinal);
    }

    public static void imprimirTabela(int parcela, LocalDate primeiroDiaMes, LocalDate ultimoDiaMes,
            BigDecimal principalAmortizacao, BigDecimal principalSaldo) {

        if (true) {
            System.out
                    .println(parcela + " | " + primeiroDiaMes + " | " + principalAmortizacao + " | " + principalSaldo);
            System.out.println(parcela + " | " + ultimoDiaMes + " | " + principalAmortizacao + " | " + principalSaldo);
        }

    }

    public static BigDecimal calculaPrincipalSaldo(int parcela, BigDecimal principalSaldo,
            BigDecimal amortizacaoAtual) {

        if (parcela < 2) {
            return principalSaldo;
        } else {
            return principalSaldo.subtract(amortizacaoAtual);
        }
    }
}
