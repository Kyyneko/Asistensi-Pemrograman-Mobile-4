package com.example.skillsaga.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillsaga.R;

import java.util.ArrayList;
import java.util.List;

public class ModuleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list);

        ImageView ivBack = findViewById(R.id.iv_back_to_learn);
        TextView tvTitle = findViewById(R.id.tv_module_category_title);
        RecyclerView rvModules = findViewById(R.id.rv_module_list);

        String kategoriMentah = getIntent().getStringExtra("KATEGORI_TERPILIH");

        final String kategori = (kategoriMentah != null) ? kategoriMentah : "Learning Materials";

        tvTitle.setText("Module: " + kategori);

        ivBack.setOnClickListener(v -> finish());

        rvModules.setLayoutManager(new LinearLayoutManager(this));
        List<ModuleModel> moduleList = generateDummyModules(kategori);

        ModuleAdapter adapter = new ModuleAdapter(moduleList, kategori, module -> {
            Intent intent = new Intent(ModuleListActivity.this, ModuleReadActivity.class);
            intent.putExtra("JUDUL_MATERI", module.title);
            intent.putExtra("KATEGORI_ASAL", kategori);
            startActivity(intent);
        });

        rvModules.setAdapter(adapter);
    }

    private List<ModuleModel> generateDummyModules(String kategori) {
        List<ModuleModel> list = new ArrayList<>();
        String cat = kategori.toLowerCase();

        if (cat.contains("computer")) {
            list.add(new ModuleModel("Computer Basics", "Understand the core of hardware and software."));
            list.add(new ModuleModel("AI Prompting 101", "Master the art of talking to Artificial Intelligence."));
            list.add(new ModuleModel("How the Internet Works", "Connecting the globe through cables and signals."));
            list.add(new ModuleModel("Cybersecurity Basics", "Protecting your digital footprint."));
            list.add(new ModuleModel("Introduction to Algorithms", "How computers think and solve problems."));

        } else if (cat.contains("science")) {
            list.add(new ModuleModel("Basic Physics Laws", "Newton, gravity, and forces that move the universe."));
            list.add(new ModuleModel("The Periodic Table", "Explore the chemical elements of everything."));
            list.add(new ModuleModel("Our Solar System", "Journey through the planets and stars."));
            list.add(new ModuleModel("Human Anatomy", "How the human biological machine works."));
            list.add(new ModuleModel("Genetics and DNA", "The blueprint of life and biological inheritance."));

        } else if (cat.contains("math")) {
            list.add(new ModuleModel("Algebra Fundamentals", "Solving the puzzle of the missing X."));
            list.add(new ModuleModel("Geometry Essentials", "Shapes, sizes, and the space we live in."));
            list.add(new ModuleModel("Probability & Statistics", "Making sense of data and predicting the future."));
            list.add(new ModuleModel("Fascinating Math Sequences", "Discover the Fibonacci sequence and Golden Ratio."));
            list.add(new ModuleModel("The Mystery of Prime Numbers", "The building blocks of modern cryptography."));

        } else if (cat.contains("history")) {
            list.add(new ModuleModel("World War II", "The largest global conflict in history."));
            list.add(new ModuleModel("Ancient Egypt", "The land of Pharaohs and Pyramids."));
            list.add(new ModuleModel("The Roman Empire", "All roads lead to Rome."));
            list.add(new ModuleModel("The Industrial Revolution", "The era that changed how we work."));
            list.add(new ModuleModel("The Cold War Era", "A war fought without direct combat."));

        } else if (cat.contains("geography")) {
            list.add(new ModuleModel("Continents and Oceans", "Mapping our blue planet."));
            list.add(new ModuleModel("Extreme Climates", "From scorching heat to freezing cold."));
            list.add(new ModuleModel("Wonders of the World", "Masterpieces of human engineering."));
            list.add(new ModuleModel("Plate Tectonics", "The moving puzzle pieces of Earth."));
            list.add(new ModuleModel("The Ring of Fire", "Earth's most explosive neighborhood."));

        } else if (cat.contains("art")) {
            list.add(new ModuleModel("The Renaissance Period", "The rebirth of art and culture."));
            list.add(new ModuleModel("Impressionism Magic", "Capturing the fleeting moment."));
            list.add(new ModuleModel("Color Theory Basics", "The science behind what we see."));
            list.add(new ModuleModel("Modern Digital Art", "Painting with pixels."));
            list.add(new ModuleModel("Famous Sculptures", "Breathing life into stone."));

        } else if (cat.contains("sport")) {
            list.add(new ModuleModel("Olympic Games History", "The ultimate test of athleticism."));
            list.add(new ModuleModel("Football Fundamentals", "The beautiful game!"));
            list.add(new ModuleModel("Basketball Rules", "Dribble, pass, and shoot."));
            list.add(new ModuleModel("Martial Arts Disciplines", "Discipline of the mind and body."));
            list.add(new ModuleModel("Tennis Grand Slams", "The pinnacle of racket sports."));

        } else if (cat.contains("animals")) {
            list.add(new ModuleModel("The Mammal Kingdom", "Warm-blooded wonders."));
            list.add(new ModuleModel("Reptiles and Amphibians", "Masters of adaptation."));
            list.add(new ModuleModel("Creatures of the Deep Sea", "Aliens of our own planet."));
            list.add(new ModuleModel("Apex Predators", "At the top of the food chain."));
            list.add(new ModuleModel("Bizarre Insect Adaptations", "The tiny rulers of Earth."));

        } else if (cat.contains("book")) {
            list.add(new ModuleModel("Classic Dystopian Novels", "Warnings of a dark future."));
            list.add(new ModuleModel("Cyberpunk Futures", "High tech, low life."));
            list.add(new ModuleModel("The Art of World-Building", "Crafting imaginary universes."));
            list.add(new ModuleModel("Dark Fantasy Realms", "Where magic meets horror."));
            list.add(new ModuleModel("Supernatural Tropes in Fiction", "Ghosts, demons, and the unknown."));

        } else if (cat.contains("mythology")) {
            list.add(new ModuleModel("The Greek Pantheon", "Gods of Mount Olympus."));
            list.add(new ModuleModel("Norse Gods and Ragnarok", "Warriors of Asgard."));
            list.add(new ModuleModel("Egyptian Deities", "Gods with animal heads."));
            list.add(new ModuleModel("Japanese Folklore & Yokai", "Spirits of the natural world."));
            list.add(new ModuleModel("Mythical Creatures", "Beasts of legend."));

        } else if (cat.contains("vehicles")) {
            list.add(new ModuleModel("How Combustion Engines Work", "The power of controlled explosions."));
            list.add(new ModuleModel("The Evolution of Airplanes", "Conquering the skies."));
            list.add(new ModuleModel("Electric Vehicles (EVs)", "The silent and green transportation revolution."));
            list.add(new ModuleModel("High-Speed Bullet Trains", "Flying on land with magnets and tracks."));
            list.add(new ModuleModel("Sailing and Navigation", "Harnessing the wind and stars."));

        } else if (cat.contains("general")) {
            list.add(new ModuleModel("The United Nations", "Striving for global peace and cooperation."));
            list.add(new ModuleModel("World Currencies", "The money that moves the global economy."));
            list.add(new ModuleModel("Famous Explorers", "Charting the unknown frontiers."));
            list.add(new ModuleModel("Global Etiquette", "Manners matter in different cultures."));
            list.add(new ModuleModel("The History of Timekeeping", "Measuring the march of time."));

        } else {
                for (int i = 1; i <= 5; i++) {
                    list.add(new ModuleModel(kategori + " Masterclass Part " + i, "An introduction to the fundamental concepts of " + kategori + "."));
                }
            }

        return list;
    }

    public static class ModuleModel {
        String title, desc;
        public ModuleModel(String title, String desc) {
            this.title = title;
            this.desc = desc;
        }
    }

    public static class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ViewHolder> {
        List<ModuleModel> list;
        String kategoriSaatIni;
        OnItemClickListener listener;

        public interface OnItemClickListener { void onItemClick(ModuleModel module); }

        public ModuleAdapter(List<ModuleModel> list, String kategoriSaatIni, OnItemClickListener listener) {
            this.list = list;
            this.kategoriSaatIni = kategoriSaatIni;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ModuleModel model = list.get(position);
            holder.tvTitle.setText(model.title);
            holder.tvDesc.setText(model.desc);

            holder.ivIcon.setImageResource(getIconBerdasarkanKategori(kategoriSaatIni));
            holder.cvIconBg.setCardBackgroundColor(android.graphics.Color.parseColor(getBgColorBerdasarkanKategori(kategoriSaatIni)));

            holder.itemView.setOnClickListener(v -> listener.onItemClick(model));
        }

        @Override
        public int getItemCount() { return list.size(); }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvDesc;
            androidx.cardview.widget.CardView cvIconBg;
            ImageView ivIcon;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_module_title);
                tvDesc = itemView.findViewById(R.id.tv_module_desc);
                cvIconBg = itemView.findViewById(R.id.cv_module_icon_bg);
                ivIcon = itemView.findViewById(R.id.iv_module_icon);
            }
        }

        private int getIconBerdasarkanKategori(String kategori) {
            if (kategori == null) return R.drawable.icon_umum;
            switch (kategori.toLowerCase()) {
                case "science": return R.drawable.icon_sains;
                case "mathematics": return R.drawable.icon_matematika;
                case "computer": return R.drawable.icon_komputer;
                case "history": return R.drawable.icon_sejarah;
                case "geography": return R.drawable.icon_geografi;
                case "sports": return R.drawable.icon_olahraga;
                case "arts": return R.drawable.icon_seni;
                case "animals": return R.drawable.icon_hewan;
                case "vehicles": return R.drawable.icon_kendaraan;
                case "mythology": return R.drawable.icon_mitologi;
                case "literature": return R.drawable.icon_sastra;
                case "general": default: return R.drawable.icon_umum;
            }
        }

        private String getBgColorBerdasarkanKategori(String kategori) {
            if (kategori == null) return "#ECEFF1";
            switch (kategori.toLowerCase()) {
                case "science": return "#E8F5E9";
                case "mathematics": return "#FFF3E0";
                case "computer": return "#E3F2FD";
                case "history": return "#FCE4EC";
                case "geography": return "#E0F2F1";
                case "sports": return "#FFF8E1";
                case "arts": return "#F3E5F5";
                case "animals": return "#EFEBE9";
                case "vehicles": return "#E8EAF6";
                case "mythology": return "#E8EAF6";
                case "literature": return "#F3E5F5";
                case "general": default: return "#ECEFF1";
            }
        }
    }
}