package com.andrysheq.deal.config;

import com.andrysheq.deal.dto.ApplicationStatusHistoryDTO;
import com.andrysheq.deal.dto.CreditDTO;
import com.andrysheq.deal.dto.LoanOfferDTO;
import com.andrysheq.deal.entity.Credit;
import com.andrysheq.deal.mapper.BaseMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PUBLIC;

@Configuration
public class MapperConfiguration {

    @Bean
    public BaseMapper modelMapper() {
        BaseMapper mapper = new BaseMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)
                .setFieldAccessLevel(PUBLIC);
        //.setPropertyCondition(context -> !(context.getSource() instanceof PersistentCollection));

        createTypeMaps(mapper);

        return mapper;
    }

    private void createTypeMaps(BaseMapper mapper) {
        mapper.createTypeMap(Credit.class, CreditDTO.class)
                .addMappings(expr -> {
                    expr.skip(CreditDTO::setAmount);
                    expr.skip(CreditDTO::setPsk);
                    expr.skip(CreditDTO::setRate);
                    expr.skip(CreditDTO::setTerm);
                    expr.skip(CreditDTO::setMonthlyPayment);
                    expr.skip(CreditDTO::setIsInsuranceEnabled);
                    expr.skip(CreditDTO::setIsSalaryClient);
                    expr.skip(CreditDTO::setPaymentSchedule);
                })
                .setPostConverter(
                        ctx -> {
                            final Credit source = ctx.getSource();
                            final CreditDTO destination = ctx.getDestination();

                            destination.setPsk(source.getPsk());
                            destination.setAmount(source.getAmount());
                            destination.setTerm(source.getTerm());
                            destination.setRate(source.getRate());
                            destination.setIsInsuranceEnabled(source.getIsInsuranceEnabled());
                            destination.setIsSalaryClient(source.getIsSalaryClient());
                            destination.setPaymentSchedule(source.getPaymentSchedule());
                            destination.setMonthlyPayment(source.getMonthlyPayment());

                            return ctx.getDestination();
                        });

        mapper.createTypeMap(CreditDTO.class, Credit.class)
                .addMappings(expr -> {
                    expr.skip(Credit::setAmount);
                    expr.skip(Credit::setPsk);
                    expr.skip(Credit::setRate);
                    expr.skip(Credit::setTerm);
                    expr.skip(Credit::setMonthlyPayment);
                    expr.skip(Credit::setIsInsuranceEnabled);
                    expr.skip(Credit::setIsSalaryClient);
                    expr.skip(Credit::setPaymentSchedule);
                })
                .setPostConverter(
                        ctx -> {
                            final CreditDTO source = ctx.getSource();
                            final Credit destination = ctx.getDestination();

                            destination.setPsk(source.getPsk());
                            destination.setAmount(source.getAmount());
                            destination.setTerm(source.getTerm());
                            destination.setRate(source.getRate());
                            destination.setIsInsuranceEnabled(source.getIsInsuranceEnabled());
                            destination.setIsSalaryClient(source.getIsSalaryClient());
                            destination.setPaymentSchedule(source.getPaymentSchedule());
                            destination.setMonthlyPayment(source.getMonthlyPayment());

                            return ctx.getDestination();
                        });
    }
}
