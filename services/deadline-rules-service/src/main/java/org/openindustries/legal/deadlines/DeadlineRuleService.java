package org.openindustries.legal.deadlines;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DeadlineRuleService {

    @Inject
    DeadlineRuleRepository repository;

    public DeadlineRule create(CreateDeadlineRuleRequest request) {
        return repository.save(DeadlineRule.create(request.name(), request.matterType(),
                request.maxFilingWindowDays(), request.maxExtensionCount(), request.priority()));
    }

    public DeadlineRule get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "DeadlineRule not found: " + id));
    }

    public List<DeadlineRule> list() {
        return repository.findAll();
    }

    public DeadlineRule activate(UUID id) {
        DeadlineRule rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.matterType());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.matterType());
        }
        for (DeadlineRule rule : candidates) {
            if (rule.maxExtensionCount <= request.requiredMaxExtensionCount()
                    && rule.maxFilingWindowDays >= request.requiredMaxFilingWindowDays()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.matterType() + " but none meets the requirement");
    }
}
