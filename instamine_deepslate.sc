// allows instamining deepslate if the player has an efficiency 5 netherite pick and haste 2

__config() -> {
    'stay_loaded' -> true,
};

global_instamine_blocks = [
    'cobbled_deepslate',
    'deepslate',
    'end_stone'
];

__on_player_clicks_block(player, block, face) -> (
    if (player~'gamemode_id' == 3, return(0));
    if (global_instamine_blocks~str(block) == null, return(0));
    eff_level = -1;
    for(['mainhand','offhand'],
        if(holds = query(player, 'holds', _),
            [wat, count, nbt] = holds;
            if (wat ~ 'netherite_pickaxe',
                eff_level = max(eff_level, 0);
                if (lvl = nbt:('components.minecraft:enchantments.minecraft:efficiency'),
                    eff_level = max(lvl, eff_level);
                )
            )
        )
    );
    if (eff_level < 0, return(0));
    haste_effect = query(player, 'effect', 'haste');
    if (haste_effect == null || haste_effect: 0 < 1, return(0));
    harvest(player, block);
);
