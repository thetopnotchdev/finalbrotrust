package com.urahara.autofish;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Simple in-game settings screen for AutoFish. Open with the configured
 * "Open AutoFish Menu" key (default: K).
 */
public class AutoFishScreen extends Screen {

	private static final int WIDGET_WIDTH = 220;

	protected AutoFishScreen() {
		super(Component.literal("AutoFish Settings"));
	}

	@Override
	protected void init() {
		int centerX = this.width / 2;
		int y = this.height / 2 - 90;

		// Toggle on/off
		this.addRenderableWidget(Button.builder(
				toggleLabel(),
				button -> {
					AutoFishConfig.enabled = !AutoFishConfig.enabled;
					button.setMessage(toggleLabel());
				}
		).bounds(centerX - WIDGET_WIDTH / 2, y, WIDGET_WIDTH, 20).build());

		y += 28;

		// Bite sensitivity: threshold ranges roughly -0.005 (very sensitive)
		// to -0.06 (requires a very hard jerk). Slider value is 0..1.
		this.addRenderableWidget(new AbstractSliderButton(
				centerX - WIDGET_WIDTH / 2, y, WIDGET_WIDTH, 20,
				Component.literal("Bite sensitivity: " + sensitivityLabel(AutoFishConfig.biteThreshold)),
				normalize(AutoFishConfig.biteThreshold, -0.06, -0.005)
		) {
			@Override
			protected void updateMessage() {
				double threshold = denormalize(this.value, -0.06, -0.005);
				this.setMessage(Component.literal("Bite sensitivity: " + sensitivityLabel(threshold)));
			}

			@Override
			protected void applyValue() {
				AutoFishConfig.biteThreshold = denormalize(this.value, -0.06, -0.005);
			}
		});

		y += 28;

		// Settle ticks: 0..40
		this.addRenderableWidget(new AbstractSliderButton(
				centerX - WIDGET_WIDTH / 2, y, WIDGET_WIDTH, 20,
				Component.literal("Settle delay: " + AutoFishConfig.minSettleTicks + " ticks"),
				AutoFishConfig.minSettleTicks / 40.0
		) {
			@Override
			protected void updateMessage() {
				int ticks = (int) Math.round(this.value * 40);
				this.setMessage(Component.literal("Settle delay: " + ticks + " ticks"));
			}

			@Override
			protected void applyValue() {
				AutoFishConfig.minSettleTicks = (int) Math.round(this.value * 40);
			}
		});

		y += 28;

		// Reaction delay after a bite (random between min and max)
		this.addRenderableWidget(new AbstractSliderButton(
				centerX - WIDGET_WIDTH / 2, y, WIDGET_WIDTH, 20,
				Component.literal("Reaction min: " + AutoFishConfig.reactMinTicks + " ticks"),
				AutoFishConfig.reactMinTicks / (double) AutoFishConfig.MAX_REACT_LIMIT
		) {
			@Override
			protected void updateMessage() {
				int ticks = (int) Math.round(this.value * AutoFishConfig.MAX_REACT_LIMIT);
				this.setMessage(Component.literal("Reaction min: " + ticks + " ticks"));
			}

			@Override
			protected void applyValue() {
				AutoFishConfig.reactMinTicks = (int) Math.round(this.value * AutoFishConfig.MAX_REACT_LIMIT);
			}
		});

		y += 28;

		this.addRenderableWidget(new AbstractSliderButton(
				centerX - WIDGET_WIDTH / 2, y, WIDGET_WIDTH, 20,
				Component.literal("Reaction max: " + AutoFishConfig.reactMaxTicks + " ticks"),
				AutoFishConfig.reactMaxTicks / (double) AutoFishConfig.MAX_REACT_LIMIT
		) {
			@Override
			protected void updateMessage() {
				int ticks = (int) Math.round(this.value * AutoFishConfig.MAX_REACT_LIMIT);
				this.setMessage(Component.literal("Reaction max: " + ticks + " ticks"));
			}

			@Override
			protected void applyValue() {
				AutoFishConfig.reactMaxTicks = (int) Math.round(this.value * AutoFishConfig.MAX_REACT_LIMIT);
			}
		});

		y += 28;

		// Recast delay: 0..60
		this.addRenderableWidget(new AbstractSliderButton(
				centerX - WIDGET_WIDTH / 2, y, WIDGET_WIDTH, 20,
				Component.literal("Recast delay: " + AutoFishConfig.recastDelayTicks + " ticks"),
				AutoFishConfig.recastDelayTicks / 60.0
		) {
			@Override
			protected void updateMessage() {
				int ticks = (int) Math.round(this.value * 60);
				this.setMessage(Component.literal("Recast delay: " + ticks + " ticks"));
			}

			@Override
			protected void applyValue() {
				AutoFishConfig.recastDelayTicks = (int) Math.round(this.value * 60);
			}
		});

		y += 32;

		// Done
		this.addRenderableWidget(Button.builder(
				Component.literal("Done"),
				button -> this.onClose()
		).bounds(centerX - WIDGET_WIDTH / 2, y, WIDGET_WIDTH, 20).build());
	}

	private static Component toggleLabel() {
		return Component.literal("AutoFish: " + (AutoFishConfig.enabled ? "ON" : "OFF"));
	}

	private static String sensitivityLabel(double threshold) {
		if (threshold > -0.015) return "High";
		if (threshold > -0.035) return "Medium";
		return "Low";
	}

	private static double normalize(double value, double min, double max) {
		return (value - min) / (max - min);
	}

	private static double denormalize(double t, double min, double max) {
		return min + t * (max - min);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractRenderState(graphics, mouseX, mouseY, delta);
		String text = this.title.getString();
		graphics.text(this.font, text, this.width / 2 - this.font.width(text) / 2,
				this.height / 2 - 115, 0xFFFFFFFF, true);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
