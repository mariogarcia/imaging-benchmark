ruleset {
    description 'Main RuleSet'

    ruleset('rulesets/basic.xml')
    ruleset('rulesets/exceptions.xml')
    ruleset('rulesets/imports.xml')
    ruleset('rulesets/unused.xml')
    ruleset('rulesets/dry.xml')
    ruleset('rulesets/formatting.xml')
    //ruleset('rulesets/size.xml')
    ruleset('rulesets/naming.xml') {
        'MethodName' {
            doNotApplyToClassNames = '*Spec'
        }
    }
    ruleset('rulesets/convention.xml')
}

