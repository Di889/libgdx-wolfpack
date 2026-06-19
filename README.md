## 1. Identificação

- Nome: Diógenes Potrich Steca
- Curso: Sistemas de Informação

---
## 2. Proposta

O objetivo desse trabalho é desenvolver um jogo de batalha em turnos estilo roguelike, desenvolvido em java com a biblioteca LIBGDX. O jogador controla uma matilha de três lobos(Alfa, Espiritualista e Rastreador) com papéis distintos que precisam sobreviver 7 dias de inverno, caçando outras ameaças animais e gerenciando recursos.
Como funcionalidades importantes de se pontuar temos:
- O sistema de habilidades, cada lobo tendo duas habilidades únicas que aproveitam polimorfismo por meio de uma `Skill` base.
- O sistema de turnos, que toda unidade(`Unit`) age de acordo com seu atributo de velocidade(`Speed`), quanto maior antes a unidade agirá, o jogador escolhendo uma habilidade e um alvo e os inimigos controlados por heurísticas.
- O sistema de efeitos de status(`statusEffects`), que são aplicados no meio de combate onde algumas habilidades os aplicam e afetam a batalha e os estados das unidades.
- O sistema de campanha de 7 dias, que gerencia a progressão do jogador com dificuldade progressiva.
- O sistema de gerador de encontros(`encounterGenerator`), que conjuntamente com a campanha garante variabilidade entre os encontros de cada dia, assim diversificando e enriquecendo a expêriencia do jogador.
- O Leaderboard, que persiste os dados da gameplay, ao fim dela, dos jogadores e os exporta pra um outro ambiente, salvando informações-chave.

Por fim, como proposto pela professora, o trabalho é versionado e desenvolvido de maneira incremental, a maneira que preferi desenvolver foi primeiro o nucleo lógico(core), como classes puramente escritas em java, e depois integrações com a interface e ferramentas do Libgdx. Funcionalidades adicionais possívelmente consideradas no momento de desenvolvimento foram avaliadas dado escopo e tempo restante.

---
## 3. Processo de desenvolvimento 
Processo de desenvolvimento: comentários sobre etapas do desenvolvimento, incluindo detalhes técnicos sobre os recursos de orientação a objetos utilizados, sobre erros/dificuldades/soluções e sobre as contribuições de cada integrante (⚠️ não usar IA para gerar ou revisar esses comentários!)



## 4. Diagrama de classes

Diagrama de classes: imagem com diagrama de classes do projeto, com crédito à ferramenta usada

## 5. Orientacoes para execução

Orientações para execução: instalação de dependências, etc.

## 6. Resultado Final

Resultado final: demonstrar execução em GIF animado ou vídeo curto

## 7. Referências e créditos

Referências e créditos (incluindo alguns prompts, quando aplicável)
