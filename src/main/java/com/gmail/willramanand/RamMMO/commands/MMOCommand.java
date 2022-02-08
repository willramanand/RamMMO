package com.gmail.willramanand.RamMMO.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.boss.BossManager;
import com.gmail.willramanand.RamMMO.boss.Bosses;
import com.gmail.willramanand.RamMMO.player.MMOPlayer;
import com.gmail.willramanand.RamMMO.ui.ItemsScreen;
import com.gmail.willramanand.RamMMO.ui.PassivesScreen;
import com.gmail.willramanand.RamMMO.utils.ColorUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("mmo")
@Description("Base RamMMO command")
public class MMOCommand extends BaseCommand {

    private final RamMMO plugin;

    public MMOCommand(RamMMO plugin) {
        this.plugin = plugin;
    }

    @Subcommand("passive|p")
    @Description("Show passive toggles")
    public void onPassive(Player player) {
        PassivesScreen passivesScreen = new PassivesScreen(plugin, player);
        player.openInventory(passivesScreen.getInventory());
    }

    @Subcommand("locateboss")
    @Description("Show the location of the current boss")
    public void onLocate(Player player) {
        Location bossLoc = BossManager.getBossLocation();
        if (bossLoc == null) {
            player.sendMessage(ColorUtils.colorMessage("&6No boss in the world!"));
        } else {
            player.sendMessage(ColorUtils.colorMessage(String.format("%s &6has been spotted in World:&d %s &6at coords:&d %d, %d, %d", BossManager.getCurrentBoss().getCustomName(), bossLoc.getWorld().
                    getName(), bossLoc.getBlockX(), bossLoc.getBlockY(), bossLoc.getBlockZ())));
        }
    }

    @Subcommand("mobs")
    @Description("Show the mobtiers and difficulty modifier")
    public void onMobs(Player player) {
        MMOPlayer mmoPlayer = plugin.getPlayerManager().getPlayerData(player);

        player.sendMessage(ColorUtils.colorMessage("&eCurrent Personal Difficulty modifier: &d" + mmoPlayer.getPersonalDifficulty()));
        player.sendMessage(ColorUtils.colorMessage("&eMobs can now spawn at &d5 &edifferent tiers within the wild!"));
        player.sendMessage(ColorUtils.colorMessage("&fCommon Mobs: &eThese are your standard mobs."));
        player.sendMessage(ColorUtils.colorMessage("&2Uncommon Mobs: &a+50% &espeed, &a2x &edamage, &a2x &ehealth."));
        player.sendMessage(ColorUtils.colorMessage("&1Rare Mobs: &a+75% &espeed, &a2x &edamage, &a3x &ehealth."));
        player.sendMessage(ColorUtils.colorMessage("&5Epic Mobs: &c-20% &espeed, &a6x &edamage, &a3x &ehealth."));
        player.sendMessage(ColorUtils.colorMessage("&6Legendary Mobs: &c-40% &espeed, &a8x &edamage, &a4x &ehealth."));
        player.sendMessage(ColorUtils.colorMessage("&eEach mob tier increases in drops, xp and skill xp!"));
    }

    @Subcommand("item")
    @CommandPermission("rammmo.item")
    @Description("Admin only command for getting custom items")
    public void onItems(Player player) {
        ItemsScreen itemsScreen = new ItemsScreen(plugin);
        player.openInventory(itemsScreen.getInventory());
    }

    @Subcommand("boss")
    @CommandPermission("rammmo.boss")
    @CommandCompletion("@bosses")
    @Description("Admin only command for summoning bosses")
    public void onBoss(Player player, Bosses bosses) {
        BossManager.spawnBoss(bosses, player.getLocation());
    }

    @Subcommand("version|v")
    @Description("Displays plugin version and information")
    public void displayVersion(CommandSender sender) {
        sender.sendMessage(ColorUtils.colorMessage("&6---- &b" + plugin.getName() + "&6----"));
        sender.sendMessage(ColorUtils.colorMessage("&dAuthor: &eWillRam"));
        sender.sendMessage(ColorUtils.colorMessage("&dVersion: &e" + plugin.getDescription().getVersion()));
        sender.sendMessage(ColorUtils.colorMessage("&e" + plugin.getDescription().getDescription()));
    }


    @Default
    public void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }
}
